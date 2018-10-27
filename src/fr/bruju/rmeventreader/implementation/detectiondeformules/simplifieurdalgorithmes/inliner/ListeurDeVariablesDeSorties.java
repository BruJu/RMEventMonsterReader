package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.inliner;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.BlocConditionnel;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffichage;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.VisiteurDAlgorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.Condition;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.ConditionVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.*;

import java.util.ArrayList;
import java.util.List;

public class ListeurDeVariablesDeSorties implements VisiteurDAlgorithme.IntegreConditionnel {
	private List<ExprVariable> variablesDeSortie = new ArrayList<>();

	public List<ExprVariable> get() {
		return variablesDeSortie;
	}

	@Override
	public void visit(InstructionAffectation instructionAffectation) {
		ExprVariable variable = instructionAffectation.variableAssignee;

		if (variable.estUneSortie() && !variablesDeSortie.contains(variable)) {
			variablesDeSortie.add(variable);
		}
	}

	@Override
	public void visit(InstructionAffichage instructionAffichage) {
	}


	@Override
	public void conditionDebut(Condition condition) {
	}

	@Override
	public void conditionSinon() {
	}

	@Override
	public void conditionFin() {
	}
}
