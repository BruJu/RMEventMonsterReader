package fr.bruju.rmeventreader.implementation.formulareader.formule;

public interface Valeur {
	public int getPriorite();
	public String getString();
	public int evaluate() throws CantEvaluateException, StatDependantEvaluation;
}
