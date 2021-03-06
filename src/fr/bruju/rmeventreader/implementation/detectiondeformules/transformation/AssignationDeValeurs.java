package fr.bruju.rmeventreader.implementation.detectiondeformules.transformation;

import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.*;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.condition.Condition;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.visiteurs.VisiteurDAlgorithme;

import java.util.Map;

public class AssignationDeValeurs implements VisiteurDAlgorithme {
	private ConstructeurValue constructeurValue;
	private boolean aChangeLALgorithme;

	public Algorithme assigner(Algorithme algorithme, Map<Integer, Integer> valeursInitiales) {
		aChangeLALgorithme = false;
		constructeurValue = new ConstructeurValue(valeursInitiales);
		visit(algorithme);
		return constructeurValue.get();
	}

	public boolean aFaitUneModification() {
		return aChangeLALgorithme;
	}

	@Override
	public void visit(BlocConditionnel blocConditionnel) {
		Condition condition = blocConditionnel.condition;

		int reponse = constructeurValue.commencerCondition(condition);

		if (reponse != 2) {
			blocConditionnel.siVrai.accept(this);
		}

		if (reponse == 0) {
			constructeurValue.conditionElse();
		} else {
			aChangeLALgorithme = true;
		}

		if (reponse != 1) {
			blocConditionnel.siFaux.accept(this);
		}

		constructeurValue.conditionFinie();
	}

	@Override
	public void visit(InstructionAffectation instructionAffectation) {
		constructeurValue.ajouter(instructionAffectation);
	}

	@Override
	public void visit(InstructionAffichage instructionAffichage) {
		constructeurValue.ajouter(instructionAffichage);
	}
}
