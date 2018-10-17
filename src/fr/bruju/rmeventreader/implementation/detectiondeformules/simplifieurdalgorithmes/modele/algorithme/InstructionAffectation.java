package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.ExprVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Expression;

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
	public String toString() {
		return new StringBuilder()
				.append(variableAssignee.getString())
				.append(" = ")
				.append(expression.getString())
				.toString();
	}
}
