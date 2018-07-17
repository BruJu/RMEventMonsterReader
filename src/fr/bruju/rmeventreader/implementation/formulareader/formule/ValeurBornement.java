package fr.bruju.rmeventreader.implementation.formulareader.formule;

import java.util.ArrayList;
import java.util.List;

/**
 * Valeur bornée par une autre
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
	public String getString() {
		if (valeurBornee instanceof ValeurBornement
				&& this.borneSup != ((ValeurBornement) valeurBornee).borneSup) {
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

	@Override
	public int evaluer() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		if (borneSup) {
			return Math.max(valeurBornee.evaluer(), borneePar.evaluer());
		} else {
			return Math.min(valeurBornee.evaluer(), borneePar.evaluer());
		}
	}

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

	@Override
	public List<Valeur> splash() {
		List<Valeur> list = new ArrayList<>();
		valeurBornee.splash().forEach(v -> list.add(new ValeurBornement(v, borneePar, borneSup)));
		return list;
	}

}
