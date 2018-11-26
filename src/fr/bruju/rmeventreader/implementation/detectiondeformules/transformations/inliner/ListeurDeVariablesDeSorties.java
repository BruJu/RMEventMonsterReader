package fr.bruju.rmeventreader.implementation.detectiondeformules.transformations.inliner;

import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.*;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.condition.Condition;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.*;

import java.util.ArrayList;
import java.util.List;

public class ListeurDeVariablesDeSorties implements VisiteurDAlgorithme {
	private List<ExprVariable> variablesDeSortie = new ArrayList<>();

	public List<ExprVariable> get() {
		return variablesDeSortie;
	}

	@Override
	public void visit(BlocConditionnel blocConditionnel) {

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


	public static List<ExprVariable> lireLesVariablesVivantes(Algorithme algorithme) {
		ListeurDeVariablesDeSorties listeur = new ListeurDeVariablesDeSorties();
		listeur.visit(algorithme);
		return listeur.get();
	}
}
