package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression;

import fr.bruju.rmdechiffreur.modele.Comparateur;

public class Borne implements Expression {
	public final Expression variable;
	public final Expression borne;
	public final boolean estBorneMax;
	
	

	public Borne(Expression variable, Expression borne, boolean estBorneMax) {
		this.variable = variable;
		this.borne = borne;
		this.estBorneMax = estBorneMax;
	}

	public Borne(Expression variable, Expression droite, Comparateur comparateur) {
		this(variable, droite, comparateur == Comparateur.INF || comparateur == Comparateur.INFEGAL);
	}

	@Override
	public String getString() {
		StringBuilder sb = new StringBuilder();
		if (estBorneMax) {
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
			if (estBorneMax) {
				return Math.max(a, b);
			} else {
				return Math.min(a, b);
			}
		}
	}

}
