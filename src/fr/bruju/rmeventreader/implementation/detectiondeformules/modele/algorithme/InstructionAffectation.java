package fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme;

import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.ExprVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.Expression;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.visiteurs.VisiteurDAlgorithme;
import fr.bruju.util.IndentedStringBuilder;

import java.util.Objects;

/**
 * Une instruction d'affectation a pour but d'attribuer une nouvelle valeur (expression) à une variable.
 */
public class InstructionAffectation implements InstructionGenerale {
	/** Variable modifiée */
	public final ExprVariable variableAssignee;
	/** Expression */
	public final Expression expression;

	/**
	 * Crée une nouvelle instruction d'affectation
	 * @param variableAssignee La variable modifiée
	 * @param expression La valeur prise par la variable
	 */
	public InstructionAffectation(ExprVariable variableAssignee, Expression expression) {
		this.variableAssignee = variableAssignee;
		this.expression = expression;
	}

	@Override
	public void listerTextuellement(IndentedStringBuilder listeur) {
		listeur.append(variableAssignee.getString())
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
		return variableAssignee.getString() + " = " + expression.getString();
	}
}
