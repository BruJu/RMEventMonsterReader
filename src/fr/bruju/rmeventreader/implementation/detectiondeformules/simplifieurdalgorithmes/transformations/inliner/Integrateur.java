package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.transformations.inliner;

import java.util.Map;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionGenerale;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Expression;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.ExprVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.VisiteurReecrivainDExpression;

public class Integrateur extends VisiteurReecrivainDExpression {
	private Map<ExprVariable, InstructionAffectation> variablesAIntegrerInterne;
	private Map<InstructionGenerale, Map<ExprVariable, InstructionAffectation>> variablesAIntegrer;
	

	public Integrateur(InstructionGenerale instructionAffectation, 
			Map<InstructionGenerale, Map<ExprVariable, InstructionAffectation>> variablesAIntegrer) {
		this.variablesAIntegrerInterne = variablesAIntegrer.get(instructionAffectation);
		this.variablesAIntegrer = variablesAIntegrer;
	}


	@Override
	public Expression explorer(ExprVariable composant) {
		InstructionAffectation reecriture = variablesAIntegrerInterne.get(composant);
		
		if (reecriture == null) {
			return composant;
		} else {
			Expression expression = reecriture.expression;

			if (variablesAIntegrer.containsKey(reecriture)) {
				return new Integrateur(reecriture, variablesAIntegrer).explorer(expression);
			} else {
				return expression;
			}
		}
	}
}
