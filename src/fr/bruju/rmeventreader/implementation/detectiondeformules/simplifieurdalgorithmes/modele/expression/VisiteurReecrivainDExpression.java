package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression;

public abstract class VisiteurReecrivainDExpression implements VisiteurDExpression {
	private Expression expression;
	
	public Expression explorer(Calcul composant) {
		Expression gauche = explorer(composant.gauche);
		Expression droite = explorer(composant.droite);
		
		if (gauche == composant.gauche && droite == composant.droite) {
			return composant;
		} else if (gauche == null || droite == null) {
			return null;
		} else {
			return new Calcul(gauche, composant.operande, droite);
		}
	}
	
	
	public Expression explorer(Constante composant) {
		return composant;
	}
	public Expression explorer(NombreAleatoire composant) {
		return composant;
	}
	public Expression explorer(ExprVariable composant) {
		return composant;
	}
	
	public final Expression explorer(Expression composant) {
		visit(composant);
		return expression;
	}
	
	public final void visit(Calcul composant) {
		expression = explorer(composant);
	}
	
	public final void visit(Constante composant) {
		expression = explorer(composant);
	}
	
	public final void visit(NombreAleatoire composant) {
		expression = explorer(composant);
	}
	
	public final void visit(ExprVariable composant) {
		expression = explorer(composant);
	}
}
