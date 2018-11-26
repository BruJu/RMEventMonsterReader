package fr.bruju.rmeventreader.implementation.detectiondeformules.transformations.bornage;

import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.BlocConditionnel;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.InstructionAffectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.InstructionAffichage;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.VisiteurDAlgorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.condition.ConditionVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.nouvellestransformations.RemplaceAlgorithme;

/**
 * Transforme les instructions du type "si a > b ; a = b" en "a = min(a, b)"
 * 
 * @author Bruju
 *
 */
public class Borneur extends RemplaceAlgorithme implements VisiteurDAlgorithme {
	private Algorithme nouvelAlgorithme;

	@Override
	public Algorithme simplifier(Algorithme algorithme) {
		nouvelAlgorithme = new Algorithme();
		visit(algorithme);
		return nouvelAlgorithme;
	}
	
	@Override
	public void visit(BlocConditionnel blocConditionnel) {
		if (essayertransfomerEnBorne(blocConditionnel)) {
			return;
		}
		
		Algorithme vrai = new Borneur().simplifier(blocConditionnel.siVrai);
		Algorithme faux = new Borneur().simplifier(blocConditionnel.siFaux);
		
		nouvelAlgorithme.ajouterInstruction(new BlocConditionnel(blocConditionnel.condition, vrai, faux));
	}

	private boolean essayertransfomerEnBorne(BlocConditionnel blocConditionnel) {
		if (blocConditionnel.siVrai.nombreDInstructions() != 1
				|| !blocConditionnel.siFaux.estVide()
				|| !(blocConditionnel.condition instanceof ConditionVariable)) {
			return false;
		}
			
		ConditionVariable cv = (ConditionVariable) blocConditionnel.condition;

		return new BorneurInterne(cv).completer(blocConditionnel, nouvelAlgorithme);
	}

	
	// Instructions recopiées

	@Override
	public void visit(InstructionAffectation instructionAffectation) {
		nouvelAlgorithme.ajouterInstruction(instructionAffectation);
	}

	@Override
	public void visit(InstructionAffichage instructionAffichage) {
		nouvelAlgorithme.ajouterInstruction(instructionAffichage);
	}
}
