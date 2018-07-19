package fr.bruju.rmeventreader.implementation.formulareader.formule.condition;

import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;
import fr.bruju.rmeventreader.rmdatabase.Affectation;

public class ConditionFixe implements Condition {
	boolean valeur;
	
	
	public ConditionFixe(boolean valeur) {
		this.valeur = valeur;
	}

	@Override
	public String getString() {
		return valeur ? "Vrai" : "Faux";
	}

	@Override
	public Condition revert() {
		return new ConditionFixe(!valeur);
	}

	@Override
	public int degreDeSimilitude(Condition autre) {
		return 0;
	}

	@Override
	public String getStringApresAutre(Condition autre) {
		return getString();
	}

	@Override
	public Valeur estVariableIdentiqueA() {
		return null;
	}

	@Override
	public Boolean resoudre(Affectation affectation) {
		return valeur;
	}

	@Override
	public boolean[] tester() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		return new boolean[] {valeur, valeur};
	}

	@Override
	public Condition evaluationPartielle(Affectation affectation) {
		return this;
	}

}
