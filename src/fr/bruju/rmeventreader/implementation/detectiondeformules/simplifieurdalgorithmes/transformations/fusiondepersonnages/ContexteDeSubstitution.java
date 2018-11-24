package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.transformations.fusiondepersonnages;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.ExprVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Expression;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Statistique;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.VisiteurReecrivainDExpression;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.personnage.Personnage;

import java.util.HashMap;
import java.util.Map;

public class ContexteDeSubstitution extends VisiteurReecrivainDExpression {

	private final Personnage individuSource;
	private final Personnage individuDestination;

	private Map<ExprVariable, ExprVariable> substitutions;


	public ContexteDeSubstitution(Personnage individuSource, Personnage individuDestination) {
		this.individuSource = individuSource;
		this.individuDestination = individuDestination;
		substitutions = new HashMap<>();
	}

	@Override
	public Expression explorer(ExprVariable composant) {
		ExprVariable variable = substitutions.get(composant);
		return variable == null ? composant : variable;
	}

	@Override
	public Expression explorer(Statistique composant) {
		if (composant.personnage != individuSource) {
			return composant;
		}

		String nomStatistique = composant.nom;
		Statistique statistiqueChezDestination = individuDestination.getStatistique(nomStatistique);
		return statistiqueChezDestination == null ? composant : statistiqueChezDestination;
	}


	public void enregistrerSubstitution(ExprVariable source, ExprVariable destination) {
		substitutions.put(source, destination);
	}
}