package fr.bruju.rmeventreader.implementation.detectiondeformules.transformations.fusiondepersonnages;

import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.InstructionAffectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.ExprVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.Expression;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.Statistique;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.VisiteurReecrivainDExpression;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.personnage.Personnage;

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
	public ExprVariable explorer(ExprVariable composant) {
		ExprVariable variable = substitutions.get(composant);
		return variable == null ? composant : variable;
	}

	@Override
	public ExprVariable explorer(Statistique composant) {
		if (composant.personnage != individuSource) {
			return composant;
		}

		String nomStatistique = composant.nom;
		Statistique statistiqueChezDestination = individuDestination.getStatistique(nomStatistique);
		return statistiqueChezDestination == null ? composant : statistiqueChezDestination;
	}

	public InstructionAffectation substituer(InstructionAffectation instruction) {
		ExprVariable gauche = (ExprVariable) explorer((Expression) instruction.variableAssignee);
		Expression droite = explorer(instruction.expression);
		return new InstructionAffectation(gauche, droite);
	}

	public void enregistrerSubstitution(InstructionAffectation source, InstructionAffectation destination) {
		enregistrerSubstitution(source.variableAssignee, destination.variableAssignee);
	}

	public void enregistrerSubstitution(ExprVariable source, ExprVariable destination) {
		substitutions.put(source, destination);
	}
}
