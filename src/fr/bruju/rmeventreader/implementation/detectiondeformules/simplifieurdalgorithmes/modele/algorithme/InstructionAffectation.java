package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.ExprVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Expression;

import java.util.Objects;

public class InstructionAffectation implements InstructionGenerale {
	public final ExprVariable variableAssignee;
	public final Expression expression;
	
	public InstructionAffectation(ExprVariable variableAssignee, Expression expression) {
		this.variableAssignee = variableAssignee;
		this.expression = expression;
	}

	@Override
	public void append(ListeurDInstructions sb) {
		sb.append(variableAssignee.getString())
		  .append(" = ")
		  .append(expression.getString())
		  .ln();
	}

	@Override
	public boolean estVide() {
		return false;
	}
	

	@Override
	public void accept(VisiteurDAlgorithme visiteur) {
		visiteur.visit(this);
	}

	@Override
	public boolean estIdentique(InstructionGenerale o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		InstructionAffectation that = (InstructionAffectation) o;
		return Objects.equals(variableAssignee, that.variableAssignee) &&
				Objects.equals(expression, that.expression);
	}


	@Override
	public int hashCode() {
		return Objects.hash(variableAssignee, expression);
	}

	@Override
	public String toString() {
		return new StringBuilder()
				.append(variableAssignee.getString())
				.append(" = ")
				.append(expression.getString())
				.toString();
	}



}
