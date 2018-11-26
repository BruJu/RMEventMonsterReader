package fr.bruju.rmeventreader.implementation.detectiondeformules.transformations.inliner;

import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.InstructionAffectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.InstructionAffichage;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.VisiteurDAlgorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.condition.Condition;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.*;

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

	public static List<ExprVariable> lireLesVariablesVivantes(Algorithme algorithme) {
		ListeurDeVariablesDeSorties listeur = new ListeurDeVariablesDeSorties();
		listeur.visit(algorithme);
		return listeur.get();
	}
}
