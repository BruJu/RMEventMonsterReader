package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme;

import fr.bruju.rmdechiffreur.modele.OpMathematique;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Calcul;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.ExprVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Expression;

public class InstructionAffectation implements InstructionGenerale {
	private ExprVariable variableAssignee;
	private Expression expression;
	
	
	public InstructionAffectation(int idVariable, OpMathematique operateur, Expression expression) {
		this.variableAssignee = new ExprVariable(idVariable);
		if (operateur == OpMathematique.AFFECTATION) {
			this.expression = expression;
		} else {
			this.expression = new Calcul(new ExprVariable(idVariable), operateur, expression);
		}
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
