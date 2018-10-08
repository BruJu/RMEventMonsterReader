package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele;

import fr.bruju.rmdechiffreur.modele.OpMathematique;

public class Instruction implements InstructionGenerale {
	private int variableAssignee;
	private Expression expression;
	
	
	public Instruction(int idVariable, OpMathematique operateur, Expression expression) {
		this.variableAssignee = idVariable;
		if (operateur == OpMathematique.AFFECTATION) {
			this.expression = expression;
		} else {
			this.expression = new Calcul(new ExprVariable(idVariable), operateur, expression);
		}
	}

	@Override
	public void append(ListeurDInstructions sb) {
		sb.append("V[" + String.format("%04d", variableAssignee) +"]")
		  .append(" = ")
		  .append(expression.getString())
		  .ln();
	}
}
