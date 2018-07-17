package fr.bruju.rmeventreader.implementation.formulareader.formule;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.utilitaire.Pair;

public class ValeurConditionnelle implements Valeur {
	private List<Pair<Condition, Valeur>> valeursConditionnelles;
	private int elementNeutre;

	public ValeurConditionnelle(Condition condition, Valeur valeur, int elementNeutre) {
		valeursConditionnelles = new ArrayList<>();
		valeursConditionnelles.add(new Pair<>(condition, valeur));
	}

	public ValeurConditionnelle(Condition condition, Valeur valeur, ValeurConditionnelle base) {
		this(condition, valeur, base.elementNeutre);
		valeursConditionnelles.addAll(base.valeursConditionnelles);
	}

	@Override
	public int evaluer() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		return evaluerMin();
	}

	@Override
	public int evaluerMin() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		for (Pair<Condition, Valeur> paireCondValeur : valeursConditionnelles) {
			Condition cond = paireCondValeur.getLeft();
			Valeur valeur = paireCondValeur.getRight();

			if (cond.testerMin()) {
				return valeur.evaluerMin();
			}
		}

		return elementNeutre;
	}

	@Override
	public int evaluerMax() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		for (Pair<Condition, Valeur> paireCondValeur : valeursConditionnelles) {
			Condition cond = paireCondValeur.getLeft();
			Valeur valeur = paireCondValeur.getRight();

			if (cond.testerMax()) {
				return valeur.evaluerMax();
			}
		}

		return elementNeutre;
	}

	@Override
	public int getPriorite() {
		return 0;
	}

	@Override
	public String getString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");

		valeursConditionnelles.forEach(paireCondValeur -> {
			Condition cond = paireCondValeur.getLeft();
			Valeur valeur = paireCondValeur.getRight();

			if (sb.toString().length() != 1) {
				sb.append(" | ");
			}

			sb.append(cond.getString()).append(" -> ").append(valeur.getString());
		}

		);

		sb.append("}");

		return sb.toString();
	}

	@Override
	public boolean estGarantiePositive() {
		if (this.elementNeutre <= 0)
			return false;
		
		for (Pair<Condition, Valeur> paireCondValeur : valeursConditionnelles) {
			Valeur valeur = paireCondValeur.getRight();
			
			if (!valeur.estGarantiePositive()) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public boolean estGarantieDeLaFormeMPMoinsConstante() {
		return false;
	}

	@Override
	public boolean estConstant() {
		for (Pair<Condition, Valeur> paireCondValeur : valeursConditionnelles) {
			Valeur valeur = paireCondValeur.getRight();
			
			if (!valeur.estConstant()) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public boolean concerneLesMP() {
		for (Pair<Condition, Valeur> paireCondValeur : valeursConditionnelles) {
			Valeur valeur = paireCondValeur.getRight();
			
			if (!valeur.concerneLesMP()) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public List<Valeur> splash() {
		List<Valeur> valeurs = new ArrayList<>();
		valeursConditionnelles.forEach(paire -> valeurs.add(paire.getRight()));
		return valeurs;
	}

	
}
