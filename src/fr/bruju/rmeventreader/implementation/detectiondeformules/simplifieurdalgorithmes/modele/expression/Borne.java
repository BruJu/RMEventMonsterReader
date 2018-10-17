package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression;

public class Borne implements Expression {
	public final Expression variable;
	public final Expression borne;
	public final boolean estBorneMin;
	
	

	public Borne(Expression variable, Expression borne, boolean estBorneMin) {
		this.variable = variable;
		this.borne = borne;
		this.estBorneMin = estBorneMin;
	}

	@Override
	public String getString() {
		StringBuilder sb = new StringBuilder();
		if (estBorneMin) {
			sb.append("max(");
		} else {
			sb.append("min(");
		}
		
		sb.append(variable.getString()).append(", ").append(borne.getString()).append(")");
		
		return sb.toString();
	}

	@Override
	public void accept(VisiteurDExpression visiteurDExpression) {
		visiteurDExpression.visit(this);
	}

	@Override
	public Integer evaluer() {
		Integer a = variable.evaluer();
		Integer b = borne.evaluer();
		
		if (a == null || b == null) {
			return null;
		} else {
			if (estBorneMin) {
				return Math.max(a, b);
			} else {
				return Math.min(a, b);
			}
		}
	}

}
