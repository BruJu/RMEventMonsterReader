package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression;

import java.util.Objects;

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


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Borne borne1 = (Borne) o;
		return estBorneMin == borne1.estBorneMin &&
				Objects.equals(variable, borne1.variable) &&
				Objects.equals(borne, borne1.borne);
	}

	@Override
	public int hashCode() {
		return Objects.hash(variable, borne, estBorneMin);
	}
}
