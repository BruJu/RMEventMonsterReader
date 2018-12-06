package fr.bruju.rmeventreader.implementation.chercheurdevariables.module;

import fr.bruju.rmdechiffreur.ExecuteurInstructions;
import fr.bruju.rmdechiffreur.modele.*;
import fr.bruju.rmdechiffreur.reference.Reference;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.BaseDeRecherche;

import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;


public class ModifVariablePuisAppelEvenement implements BaseDeRecherche {

	private final int numeroVariable;
	private Set<Reference> referencesConnues = new TreeSet<>();

	private final int numeroEvenement;




	public ModifVariablePuisAppelEvenement(int numeroVaraible, int numeroEvenement) {
		this.numeroVariable = numeroVaraible;
		this.numeroEvenement = numeroEvenement;
	}

	@Override
	public void afficher() {
		referencesConnues.forEach(reference -> System.out.println(reference.getString()));
	}

	@Override
	public ExecuteurInstructions getExecuteur(Reference ref) {
		return new Chercheur(ref);
	}

	
	public class Chercheur implements ExecuteurInstructions {
		private Reference reference;
		private boolean flip = true;

		private Stack<Boolean> entree = new Stack<>();
		private Stack<Boolean> sortie = new Stack<>();


		@Override
		public boolean SaisieMessages_initierQCM(ExecEnum.ChoixQCM choixLorsDeLAnnulation) {
			entree.push(flip);
			sortie.push(flip);
			return true;
		}

		@Override
		public void SaisieMessages_choixQCM(String texte, ExecEnum.ChoixQCM numero) {
			sortie.push(sortie.pop() && flip);
			flip = entree.peek();
		}

		@Override
		public void SaisieMessages_finQCM() {
			entree.pop();
			flip = sortie.pop();
		}

		@Override
		public int Flot_si(Condition condition) {
			entree.push(flip);
			sortie.push(flip);
			return 0;
		}

		@Override
		public void Flot_siFin() {
			entree.pop();
			flip = sortie.pop() && flip;
		}

		@Override
		public void Flot_siNon() {
			sortie.push(sortie.pop() && flip);
			flip = entree.peek();
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
			if (flip && numero == numeroEvenement) {
				referencesConnues.add(reference);
			}
		}

		@Override
		public void Variables_affecterVariable(ValeurGauche valeurGauche, ValeurDroiteVariable valeurDroite) {
			if (valeurGauche.appliquerG(v -> v.idVariable == numeroVariable, null, null) == Boolean.TRUE) {
				flip = false;
			}
		}

		@Override
		public void Variables_changerVariable(ValeurGauche valeurGauche, OpMathematique operateur, ValeurDroiteVariable valeurDroite) {

			if (valeurGauche.appliquerG(v -> v.idVariable == numeroVariable, null, null) == Boolean.TRUE) {
				flip = false;
			}
		}
	}
}
