package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression;

import static fr.bruju.rmeventreader.ProjetS.PROJET;
import java.util.Objects;

public class ExprVariable implements Expression {
	public final int idVariable;

	public ExprVariable(int idVariable) {
		this.idVariable = idVariable;
	}

	@Override
	public String getString() {
		StringBuilder sb = new StringBuilder();
		String nom;
		int coef;
		
		if (idVariable < 0) {
			coef = -1;
			sb.append("S[");
			nom = PROJET.extraireInterrupteur(-idVariable);
		} else {
			coef = 1;
			sb.append("V[");
			nom = PROJET.extraireVariable(idVariable);
		}
		
		sb.append(String.format("%04d", idVariable * coef))
		  .append(":")
		  .append(nom.trim())
		  .append("]");
		
		return sb.toString();
	}

	@Override
	public void accept(VisiteurDExpression visiteurDExpression) {
		visiteurDExpression.visit(this);
	}

	@Override
	public Integer evaluer() {
		return null;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(idVariable);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof ExprVariable) {
			ExprVariable that = (ExprVariable) object;
			return this.idVariable == that.idVariable;
		}
		return false;
	}
}
