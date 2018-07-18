package fr.bruju.rmeventreader.implementation.formulareader.formule;

import fr.bruju.rmeventreader.implementation.formulareader.model.Personnage;
import fr.bruju.rmeventreader.implementation.formulareader.model.Statistique;

public class NewValeur {

	public static ValeurNumerique Numerique(int valeur) {
		return new ValeurNumerique(valeur);
	}

	public static ValeurNumerique Numerique(int valeurMin, int valeurMax) {
		return new ValeurNumerique(valeurMin, valeurMax);
	}

	public static Valeur True() {
		return new ValeurNumerique(1);
	}

	public static Valeur False() {
		return new ValeurNumerique(0);
	}

	public static Valeur Nommee(int idVariable, String nom) {
		return new ValeurNommee(idVariable, nom);
	}

	public static Valeur Statistique(Personnage personnage, Statistique statistique) {
		return new ValeurStatistique(personnage, statistique);
	}

	public static Valeur Switch(int idSwitch) {
		return new ValeurSwitch(idSwitch);
	}

	public static Valeur Variable(int idVariable) {
		return new ValeurVariable(idVariable);
	}

	public static Valeur Ternaire(Condition condition, Valeur siVrai, Valeur siFaux) {
		Valeur factorisation = TernaireFactoriser(condition, siVrai, siFaux);

		if (factorisation != null)
			return factorisation;

		return new ValeurTernaire(condition, siVrai, siFaux);
	}

	/**
	 * Si une ternaire du type [(condition) ? x * 3 : x * 4] va être crée, crée à la place une ternaire de type x *
	 * [(condition) ? 3 : 4]
	 * 
	 */
	private static Valeur TernaireFactoriser(Condition condition, Valeur siVrai, Valeur siFaux) {
		if (!(condition instanceof ConditionVariable && siVrai instanceof Calcul && siFaux instanceof Calcul)) {
			return null;
		}

		ConditionVariable condV = (ConditionVariable) condition;

		if (!condV.getDroite().estConstant())
			return null;

		Calcul siVraiC = (Calcul) siVrai;
		Calcul siFauxC = (Calcul) siFaux;

		if (!siVraiC.getOperateur().equals(siFauxC.getOperateur()))
			return null;

		if (siVraiC.getGauche() == siFauxC.getGauche()) {
			return Calcul(siVraiC.getGauche(), siVraiC.getOperateur(),
					Ternaire(condV, siVraiC.getDroite(), siFauxC.getDroite()));
		}

		if (siVraiC.getDroite() == siFauxC.getDroite()) {
			return Calcul(Ternaire(condV, siVraiC.getGauche(), siFauxC.getGauche()), siVraiC.getOperateur(),
					siVraiC.getDroite());
		}

		return null;
	}

	public static Valeur Calcul(Valeur leftValue, String symbole, Valeur rightValue) {
		return new Calcul(leftValue, symbole, rightValue);
	}

	public static ValeurConditionnelle ValeurConditionnelle(Condition condition, Valeur valeur, int neutre) {
		return new ValeurConditionnelle(condition, valeur, neutre);
	}
	
	
	public static Valeur Unification(Condition condition, Valeur valeurBDD, Valeur valeurGauche, Valeur valeurDroite) {
		if (valeurGauche != null && valeurDroite != null) {
			return Ternaire(condition, (valeurGauche == null) ? valeurBDD : valeurGauche, (valeurDroite == null) ? valeurBDD : valeurDroite);
		}
		
		if (valeurGauche != null && valeurGauche instanceof Calcul) {
			Calcul calc = (Calcul) valeurGauche;
			
			if (calc.getGauche() == valeurBDD) {
				return UnificationEnCours(valeurBDD, calc.getOperateur(), ValeurConditionnelle(condition, calc.getDroite(), getNeutre(calc.getOperateur())));
			}

			if (calc.getDroite() == valeurBDD) {
				return UnificationEnCours(valeurBDD, calc.getOperateur(), ValeurConditionnelle(condition, calc.getGauche(), getNeutre(calc.getOperateur())));
			}
		}

		if (valeurDroite != null && valeurDroite instanceof Calcul) {
			Calcul calc = (Calcul) valeurDroite;
			
			if (calc.getGauche() == valeurBDD) {
				return UnificationEnCours(valeurBDD, calc.getOperateur(), ValeurConditionnelle(condition.revert(), calc.getDroite(), getNeutre(calc.getOperateur())));
			}

			if (calc.getDroite() == valeurBDD) {
				return UnificationEnCours(valeurBDD, calc.getOperateur(), ValeurConditionnelle(condition.revert(), calc.getGauche(), getNeutre(calc.getOperateur())));
			}
		}

		
		

		return Ternaire(condition, (valeurGauche == null) ? valeurBDD : valeurGauche, (valeurDroite == null) ? valeurBDD : valeurDroite);
	}

	private static Valeur UnificationEnCours(Valeur valeurBDD, String operateur, ValeurConditionnelle valeurConditionnelle) {

		if (valeurBDD instanceof Calcul) {
			Calcul calculBDD = (Calcul) valeurBDD;
			
			if (calculBDD.getOperateur().equals(operateur) && calculBDD.getDroite() instanceof ValeurConditionnelle) {
				ValeurConditionnelle nouveauDroite = new ValeurConditionnelle(valeurConditionnelle, (ValeurConditionnelle) calculBDD.getDroite());
				
				return Calcul(calculBDD.getGauche(), operateur, nouveauDroite);
			}

			if (calculBDD.getOperateur().equals(operateur) && calculBDD.getGauche() instanceof ValeurConditionnelle) {
				ValeurConditionnelle nouveauDroite = new ValeurConditionnelle(valeurConditionnelle, (ValeurConditionnelle) calculBDD.getGauche());
				
				return Calcul(calculBDD.getDroite(), operateur, nouveauDroite);
			}
			
		}
		return Calcul(valeurBDD, operateur, valeurConditionnelle);
	}

	private static int getNeutre(String operateur) {
		switch (operateur) {
		case "*":
		case "/":
			return 1;
		case "%":
			return Integer.MAX_VALUE;
		case "+":
		case "-":
			return 0;
		default:
			throw new RuntimeException("Opérateur inconnu");
		}
	}

}