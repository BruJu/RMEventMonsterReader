package fr.bruju.rmeventreader.implementation.detectiondeformules.transformation.inliner;

import java.util.Map;

import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.InstructionAffectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.InstructionGenerale;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.Expression;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.ExprVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.visiteurs.VisiteurReecrivainDExpression;

/**
 * Construit des expressions en utilisant une table associant à chaque instruction d'affectation la liste des
 * variables qu'elle peut remplacer par une expression.
 */
public class InlinerDExpressions extends VisiteurReecrivainDExpression {
	/**
	 * Associations variables - instruction d'affectation décrivant le contenu de la variable pour les expressions
	 * traitées par cet inliner
	 */
	private Map<ExprVariable, InstructionAffectation> variablesAIntegrerInterne;
	/** Toutes les inlinations possibles (utile pour la récursion) */
	private Map<InstructionGenerale, Map<ExprVariable, InstructionAffectation>> variablesAIntegrer;

	/**
	 * Crée un inliner d'expressions pour l'instruction donnée et utilisant la table d'association donnée.
	 * <br><br>
	 * Les subsitutions sont fait récursivement.
	 * <br>Exemple : On a x = 3; y = x; z = y;
	 * <br>z = y doit inliner le contenu de y. Lors de l'inlination, on va voir que y doit inliner le contenu de x. Donc
	 * z = y sera remplacé au final par z = y = contenu de y = x = contenu de x = 3 -> z = 3;
	 * @param instructionAffectation L'instruction va va être traitée
	 * @param variablesAIntegrer Une table de hashage associant à chaque instruction la liste des variables qu'elle
	 *                           peut substituer. La liste des variables se présente sous la forme d'associations entre
	 *                           variables et instruction d'affectation à intégrer.
	 */
	public InlinerDExpressions(InstructionGenerale instructionAffectation,
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
				return new InlinerDExpressions(reecriture, variablesAIntegrer).explorer(expression);
			} else {
				return expression;
			}
		}
	}
}
