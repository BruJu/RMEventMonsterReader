package fr.bruju.rmeventreader.implementation.chercheurdevariables.module;

import fr.bruju.rmdechiffreur.ExecuteurInstructions;
import fr.bruju.rmdechiffreur.controlleur.ExtChangeVariable;
import fr.bruju.rmdechiffreur.modele.OpMathematique;
import fr.bruju.rmdechiffreur.modele.ValeurFixe;
import fr.bruju.rmdechiffreur.modele.Variable;
import fr.bruju.rmdechiffreur.reference.Reference;
import fr.bruju.rmdechiffreur.reference.ReferenceEC;
import fr.bruju.rmdechiffreur.reference.ReferenceMap;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.BaseDeRecherche;

import java.util.*;

import static fr.bruju.rmeventreader.ProjetS.PROJET;

public class DoubleVariableAjoute implements BaseDeRecherche {
	private List<Ajout> ajouts = new ArrayList<>();

	/* On estime que le nombre de variables sera petit */
	private List<Integer> variablesTrackees;


	public DoubleVariableAjoute(List<Integer> variablesTrackees) {
		this.variablesTrackees = variablesTrackees;
	}

	@Override
	public void afficher() {
		Collections.sort(ajouts);

		for (Ajout ajout : ajouts) {
			System.out.println(ajout.toString());
		}
	}

	@Override
	public ExecuteurInstructions getExecuteur(Reference ref) {
		return new Executeur(ref);
	}

	private static class Ajout implements Comparable<Ajout> {
		private final String lieu;
		private final int variable;
		private final int bonus;

		public Ajout(String lieu, int variable, int bonus) {
			this.lieu = lieu;
			this.variable = variable;
			this.bonus = bonus;
		}

		@Override
		public int compareTo(Ajout o) {
			int cmp;

			cmp = lieu.compareTo(o.lieu);
			if (cmp != 0) return cmp;

			cmp = Integer.compare(variable, o.variable);
			if (cmp != 0) return cmp;

			return Integer.compare(bonus, o.bonus);
		}

		@Override
		public String toString() {
			return lieu + ";" + PROJET.extraireVariable(variable) + ";" + bonus;
		}
	}

	private class Executeur implements ExecuteurInstructions, ExtChangeVariable {
		private final String lieu;

		public Executeur(Reference ref) {
			if (ref instanceof ReferenceEC) {
				lieu = "EC" + ((ReferenceEC) ref).eventCommun + " " + ((ReferenceEC) ref).nom;
			} else { //if (ref instanceof ReferenceMap)
				ReferenceMap refMap = (ReferenceMap) ref;
				lieu = refMap.nomMap + " " + refMap.nomEvent;
			}
		}

		@Override
		public boolean getBooleenParDefaut() {
			return true;
		}

		@Override
		public void changerVariable(Variable valeurGauche, OpMathematique operateur, ValeurFixe valeurDroite) {
			if (variablesTrackees.contains(valeurGauche.idVariable) && operateur == OpMathematique.PLUS) {
				ajouts.add(new Ajout(lieu, valeurGauche.idVariable, valeurDroite.valeur));
			}
		}

		@Override
		public void affecterVariable(Variable valeurGauche, ValeurFixe valeurDroite) {
			if (variablesTrackees.contains(valeurGauche.idVariable)) {
				ajouts.add(new Ajout(lieu, valeurGauche.idVariable, valeurDroite.valeur));
			}
		}
	}
}
