package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Expression;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.VariableInstanciee;

public class InstructionAffectation implements InstructionGenerale {
	public final VariableInstanciee variableAssignee;
	public final Expression expression;
	
	public InstructionAffectation(VariableInstanciee variableAssignee, Expression expression) {
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
}
