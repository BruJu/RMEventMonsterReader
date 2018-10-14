package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.executeur;

import java.util.Map;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Constante;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.ExprVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Expression;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.VisiteurReecrivainDExpression;

public class SubstitutionDeValeurs extends VisiteurReecrivainDExpression {

	private Map<Integer, Integer> valeurDesVariables;

	public SubstitutionDeValeurs(Map<Integer, Integer> valeurDesVariables) {
		this.valeurDesVariables = valeurDesVariables;
	}

	@Override
	public Expression explorer(ExprVariable composant) {
		Integer valeur = valeurDesVariables.get(composant.idVariable);
		
		return valeur == null ? composant : new Constante(valeur);
	}

}
