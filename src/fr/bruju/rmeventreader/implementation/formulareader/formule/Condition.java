package fr.bruju.rmeventreader.implementation.formulareader.formule;


public interface Condition {
	public boolean tester() throws NonEvaluableException, DependantDeStatistiquesEvaluation;
	
	public boolean testerMax() throws NonEvaluableException, DependantDeStatistiquesEvaluation;
	
	public boolean testerMin() throws NonEvaluableException, DependantDeStatistiquesEvaluation;
	
	public String getString();
	
	public Condition revert();
	
	public int degreDeSimilitude(Condition autre);
	
	public String getStringApresAutre(Condition autre);
	
	public Valeur estVariableIdentiqueA();
}