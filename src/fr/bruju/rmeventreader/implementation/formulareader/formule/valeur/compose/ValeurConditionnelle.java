package fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.compose;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.FonctionEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;
import fr.bruju.rmeventreader.implementation.formulareader.formule.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulareader.formule.condition.ConditionVariable;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;
import fr.bruju.rmeventreader.rmdatabase.Affectation;
import fr.bruju.rmeventreader.utilitaire.Pair;

public class ValeurConditionnelle implements Valeur {
	private List<Pair<Condition, Valeur>> valeursConditionnelles;
	private int elementNeutre;

	public ValeurConditionnelle(Condition condition, Valeur valeur, int elementNeutre) {
		valeursConditionnelles = new ArrayList<>();
		valeursConditionnelles.add(new Pair<>(condition, valeur));
		this.elementNeutre = elementNeutre;
	}

	public ValeurConditionnelle(ValeurConditionnelle gauche, ValeurConditionnelle droite) {
		valeursConditionnelles = new ArrayList<>();
		this.elementNeutre = droite.elementNeutre;

		valeursConditionnelles.addAll(gauche.valeursConditionnelles);
		valeursConditionnelles.addAll(droite.valeursConditionnelles);
	}
	

	public int evaluer(FonctionEvaluation fonction) throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		for (Pair<Condition, Valeur> paireCondValeur : valeursConditionnelles) {
			Condition cond = paireCondValeur.getLeft();
			Valeur valeur = paireCondValeur.getRight();

			if (fonction.tester(cond)) {
				return fonction.evaluate(valeur);
			}
		}

		return elementNeutre;
	}
	
	@Override
	public int evaluerMin() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		return evaluer(FonctionEvaluation.minimum);
	}

	@Override
	public int evaluerMax() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		return evaluer(FonctionEvaluation.maximum);
	}

	@Override
	public int getPriorite() {
		return 0;
	}

	@Override
	public String getString() {
		if (affichageEnsemblistePossible()) {
			return getAffichageEnsembliste();
		}

		StringBuilder sb = new StringBuilder();
		sb.append("{");

		Condition derniereConditionVue = null;

		for (Pair<Condition, Valeur> paireCondValeur : valeursConditionnelles) {
			Condition cond = paireCondValeur.getLeft();
			Valeur valeur = paireCondValeur.getRight();

			if (sb.toString().length() != 1) {
				sb.append(" | ");
			}

			sb.append(cond.getStringApresAutre(derniereConditionVue)).append(" -> ").append(valeur.getString());

			derniereConditionVue = cond;
		}

		sb.append("}");

		return sb.toString();
	}

	private String getAffichageEnsembliste() {
		String valeursDepart = "[";
		String valeursSortie = "[";
		String valeurTestee = ((ConditionVariable) valeursConditionnelles.get(0).getLeft()).getGauche().getString();
		
		for (Pair<Condition, Valeur> paireCondValeur : valeursConditionnelles) {
			ConditionVariable c = (ConditionVariable) paireCondValeur.getLeft();
			
			valeursDepart = valeursDepart + c.getDroite().getString()+ ", ";
			valeursSortie = valeursSortie + paireCondValeur.getRight().getString()+ ", ";
			
		}

		
		valeursDepart = valeursDepart.substring(0, valeursDepart.length() - 2) + "]";
		valeursSortie = valeursSortie.substring(0, valeursSortie.length() - 2) + "]";

		return "{" + valeurTestee + valeursDepart + " = " + valeursSortie + "}";
	}

	private boolean affichageEnsemblistePossible() {
		if (valeursConditionnelles.size() < 2) {
			return false;
		}

		Valeur valeurCommune = valeursConditionnelles.get(0).getLeft().estVariableIdentiqueA();

		if (valeurCommune == null)
			return false;

		for (Pair<Condition, Valeur> paireCondValeur : valeursConditionnelles) {
			if (paireCondValeur.getLeft().degreDeSimilitude(valeursConditionnelles.get(0).getLeft()) != 3) {
				return false;
			}
		}
		
		
		return valeurCommune != null;
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
	public Valeur evaluationPartielle(Affectation affectation) {
		// TODO Auto-generated method stub
		return null;
	}

}
