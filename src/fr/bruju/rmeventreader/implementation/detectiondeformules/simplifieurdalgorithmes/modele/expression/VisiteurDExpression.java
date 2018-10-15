package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression;

public interface VisiteurDExpression {
	public default void visit(Expression expression) {
		expression.accept(this);
	}
	
	public void visit(Calcul composant);
	public void visit(Constante composant);
	public void visit(NombreAleatoire composant);
	public void visit(ExprVariable composant);
	public void visit(Borne composant);
	
}
