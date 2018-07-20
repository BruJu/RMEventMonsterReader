package fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.compose;

import java.util.List;

import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;
import fr.bruju.rmeventreader.implementation.formulareader.formule.condition.Condition;
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

	@Override
	public int[] evaluer() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		int[] intValeurBornee = valeurBornee.evaluer();
		int[] intBorneePar = valeurBornee.evaluer();
		
		int valeurInf;
		int valeurSup;
		
		if (borneSup) {
			valeurInf = Math.min(intValeurBornee[0], intBorneePar[0]);
			valeurSup = Math.min(intValeurBornee[1], intBorneePar[1]);
		} else {
			valeurInf = Math.max(intValeurBornee[0], intBorneePar[0]);
			valeurSup = Math.max(intValeurBornee[1], intBorneePar[1]);
		}
		
		return new int[] {valeurInf, valeurSup};
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
	public Valeur integrerCondition(List<Condition> aInclure) {
		return new ValeurBornement(valeurBornee.integrerCondition(aInclure), (ValeurNumerique) borneePar.integrerCondition(aInclure), borneSup);
	}



}
