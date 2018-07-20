package fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.compose;

import java.util.function.UnaryOperator;

import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.simple.ValeurNumerique;

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
	public Valeur deleguerTraitement(UnaryOperator<Valeur> conversion) {
		return new ValeurBornement(conversion.apply(valeurBornee),
				borneePar,
				borneSup);
	}
	
	
	@Override
	public Valeur simplifier() {
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (borneSup ? 1231 : 1237);
		result = prime * result + ((borneePar == null) ? 0 : borneePar.hashCode());
		result = prime * result + ((valeurBornee == null) ? 0 : valeurBornee.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ValeurBornement other = (ValeurBornement) obj;
		if (borneSup != other.borneSup)
			return false;
		if (borneePar == null) {
			if (other.borneePar != null)
				return false;
		} else if (!borneePar.equals(other.borneePar))
			return false;
		if (valeurBornee == null) {
			if (other.valeurBornee != null)
				return false;
		} else if (!valeurBornee.equals(other.valeurBornee))
			return false;
		return true;
	}




}
