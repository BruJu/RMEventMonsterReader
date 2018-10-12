package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression;

public interface Expression {

	public String getString();

	public void accept(VisiteurDExpression visiteurDExpression);
	
	public Integer evaluer();

}
