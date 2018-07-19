package fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.compose;

import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.FonctionEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.simple.ValeurNumerique;
import fr.bruju.rmeventreader.rmdatabase.Affectation;

/**
 * Valeur bornée par une autre
 * 
 * @author Bruju
 *
 */
public class ValeurBornement implements Valeur {
	private Valeur valeurBornee;
	private ValeurNumerique borneePar;
	private boolean borneSup;

	public ValeurBornement(Valeur valeurBornee, ValeurNumerique borneePar, boolean borneSup) {
		this.valeurBornee = valeurBornee;
		this.borneePar = borneePar;
		this.borneSup = borneSup;
	}

	@Override
	public int getPriorite() {
		return 0;
	}
	
	@Override
	public Valeur evaluationPartielle(Affectation affectation) {
		Valeur nouvelleValeurBornee = valeurBornee.evaluationPartielle(affectation);

		return new ValeurBornement(nouvelleValeurBornee, borneePar, borneSup);
	}

	
	/* ==========
	 * EVALUATION
	 * ========== */

	/**
	 * Evalue la valeur en fonction d'une fonction d'évaluation des valeurs la composant
	 */
	public int evaluer(FonctionEvaluation fonctionEvaluation)
			throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		if (borneSup) {
			return Math.max(fonctionEvaluation.evaluate(valeurBornee), fonctionEvaluation.evaluate(borneePar));
		} else {
			return Math.min(fonctionEvaluation.evaluate(valeurBornee), fonctionEvaluation.evaluate(borneePar));
		}
	}

	@Override
	public int evaluerMin() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		return evaluer(FonctionEvaluation.minimum);
	}

	@Override
	public int evaluerMax() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		return evaluer(FonctionEvaluation.maximum);
	}

	/* =========
	 * AFFICHAGE
	 * ========= */
	
	@Override
	public String getString() {
		if (valeurBornee instanceof ValeurBornement && this.borneSup != ((ValeurBornement) valeurBornee).borneSup) {
			ValeurBornement autreBorne = (ValeurBornement) valeurBornee;
			String borneInf;
			String borneSup;
			String valeur;

			if (this.borneSup) {
				borneInf = autreBorne.borneePar.getString();
				borneSup = this.borneePar.getString();
			} else {
				borneSup = autreBorne.borneePar.getString();
				borneInf = this.borneePar.getString();
			}

			valeur = autreBorne.valeurBornee.getString();

			return "entre(" + borneInf + ", " + valeur + ", " + borneSup + ")";
		}

		String nom = borneSup ? "max" : "min";

		return nom + "(" + valeurBornee.getString() + ", " + borneePar.getString() + ")";
	}

	/* ============
	 * HEURISTIQUES
	 * ============ */
	
	@Override
	public boolean estGarantiePositive() {
		return (borneSup && valeurBornee.estGarantiePositive()) || (!borneSup && borneePar.estGarantiePositive());
	}

	@Override
	public boolean estGarantieDeLaFormeMPMoinsConstante() {
		return valeurBornee.estGarantieDeLaFormeMPMoinsConstante();
	}

	@Override
	public boolean estConstant() {
		return valeurBornee.estConstant();
	}

	@Override
	public boolean concerneLesMP() {
		return valeurBornee.concerneLesMP();
	}

}
