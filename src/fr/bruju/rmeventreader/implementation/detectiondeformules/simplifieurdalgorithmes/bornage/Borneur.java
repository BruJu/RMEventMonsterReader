package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.bornage;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.Simplification;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.BlocConditionnel;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffichage;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.VisiteurDAlgorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.ConditionVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Borne;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.ExprVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Expression;

/**
 * Transforme les instructions du type "si a > b ; a = b" en "a = max(a, b)"
 * 
 * @author Bruju
 *
 */
public class Borneur implements VisiteurDAlgorithme, Simplification {
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
		
		Expression gauche = cv.gauche;
		Expression droite = cv.droite;
		
		BorneurInterne borneurInterne = new BorneurInterne(gauche, droite);
		borneurInterne.visit(blocConditionnel.siVrai);
		
		if (!borneurInterne.bornageReussit()) {
			return false;
		}
		
		InstructionAffectation affectationBornee = creerAffectationDeBornage(cv);
		
		nouvelAlgorithme.ajouterInstruction(affectationBornee);
		return true;
	}

	private InstructionAffectation creerAffectationDeBornage(ConditionVariable cv) {
		ExprVariable variable = (ExprVariable) cv.gauche;
		Expression expressionBorne = new Borne(cv.gauche, cv.droite, cv.comparateur);
		
		return new InstructionAffectation(variable, expressionBorne);
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
