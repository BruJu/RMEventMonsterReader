package fr.bruju.rmeventreader.implementation.chercheurdevariables.module;

import fr.bruju.rmdechiffreur.ExecuteurInstructions;
import fr.bruju.rmdechiffreur.modele.*;
import fr.bruju.rmdechiffreur.reference.Reference;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.BaseDeRecherche;

import java.util.*;


public class ModifVariablePuisAppelEvenement implements BaseDeRecherche {
	private enum Statut {
		NONMODIFIE,
		REUTILISELAVALEUR,
		MODIFIE;

		static Statut cumuler(Statut statut1, Statut statut2) {
			if (statut1 == null) {
				return statut2;
			}

			if (statut1.ordinal() > statut2.ordinal()) {
				return statut1;
			} else {
				return statut2;
			}
		}


		static Statut decumuler(Statut statut1, Statut statut2) {
			if (statut1 == null) {
				return statut2;
			}

			if (statut1.ordinal() < statut2.ordinal()) {
				return statut1;
			} else {
				return statut2;
			}
		}
	}


	private final int numeroVariable;
	private Map<Reference, Statut> referencesConnues = new TreeMap<>();

	private final int numeroEvenement;




	public ModifVariablePuisAppelEvenement(int numeroVaraible, int numeroEvenement) {
		this.numeroVariable = numeroVaraible;
		this.numeroEvenement = numeroEvenement;
	}

	@Override
	public void afficher() {
		referencesConnues.forEach((reference, statut) -> {
			if (statut == Statut.REUTILISELAVALEUR) {
				System.out.println(reference.getString() + " (Réutilise)");
			} else {
				System.out.println(reference.getString());
			}
		});
	}

	@Override
	public ExecuteurInstructions getExecuteur(Reference ref) {
		return new Chercheur(ref);
	}

	
	public class Chercheur implements ExecuteurInstructions {
		private Reference reference;
		private Statut statut = Statut.NONMODIFIE;

		private Stack<Statut> entree = new Stack<>();
		private Stack<Statut> sortie = new Stack<>();


		@Override
		public boolean SaisieMessages_initierQCM(ExecEnum.ChoixQCM choixLorsDeLAnnulation) {
			entree.push(statut);
			sortie.push(statut);
			return true;
		}

		@Override
		public void SaisieMessages_choixQCM(String texte, ExecEnum.ChoixQCM numero) {
			sortie.push(Statut.cumuler(sortie.pop(), statut));
			statut = entree.peek();
		}

		@Override
		public void SaisieMessages_finQCM() {
			entree.pop();
			statut = sortie.pop();
		}

		@Override
		public int Flot_si(Condition condition) {
			entree.push(statut);
			sortie.push(statut);
			return 0;
		}

		@Override
		public void Flot_siFin() {
			entree.pop();
			statut = Statut.cumuler(sortie.pop(), statut);
		}

		@Override
		public void Flot_siNon() {
			sortie.push(Statut.cumuler(sortie.pop(), statut));
			statut = entree.peek();
		}

		public Chercheur(Reference reference) {
			this.reference = reference;
		}

		@Override
		public boolean getBooleenParDefaut() {
			return true;
		}

		@Override
		public void Flot_appelEvenementCommun(int numero) {
			if (statut != Statut.MODIFIE && numero == numeroEvenement) {
				referencesConnues.compute(reference, (k, v) -> Statut.decumuler(v, statut));
			}
		}

		@Override
		public void Variables_affecterVariable(ValeurGauche valeurGauche, ValeurDroiteVariable valeurDroite) {
			if (valeurGauche.appliquerG(v -> v.idVariable == numeroVariable, null, null) == Boolean.TRUE) {
				statut = Statut.MODIFIE;
			}
		}

		@Override
		public void Variables_changerVariable(ValeurGauche valeurGauche, OpMathematique operateur, ValeurDroiteVariable valeurDroite) {
			if (valeurGauche.appliquerG(v -> v.idVariable == numeroVariable, null, null) == Boolean.TRUE) {
				statut = Statut.cumuler(statut, Statut.REUTILISELAVALEUR);
			}
		}
	}
}
