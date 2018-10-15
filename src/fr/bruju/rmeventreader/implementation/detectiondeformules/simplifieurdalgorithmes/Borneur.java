package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes;

import fr.bruju.rmdechiffreur.modele.Comparateur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.BlocConditionnel;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffichage;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.VisiteurDAlgorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.ConditionVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Borne;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.ExprVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Expression;

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
		if (blocConditionnel.siVrai.nombreDInstructions() == 1
				&& blocConditionnel.siFaux.estVide()
				&& blocConditionnel.condition instanceof ConditionVariable) {
			
			ConditionVariable cv = (ConditionVariable) blocConditionnel.condition;
			
			Expression gauche = cv.gauche;
			Expression droite = cv.droite;
			
			BorneurInterne borneurInterne = new BorneurInterne(gauche, droite);
			borneurInterne.visit(blocConditionnel.siVrai);
			boolean estBornable = borneurInterne.getResultat();
			
			if (estBornable) {
				ExprVariable variable = (ExprVariable) gauche;
				Expression expressionBorne = new Borne(gauche, droite, cv.comparateur);
				
				InstructionAffectation affectationBornee = new InstructionAffectation(variable, expressionBorne);
				
				nouvelAlgorithme.ajouterInstruction(affectationBornee);
				return;
			}
			
		}
		
		// Recursion
		Algorithme vrai = new Borneur().simplifier(blocConditionnel.siVrai);
		Algorithme faux = new Borneur().simplifier(blocConditionnel.siFaux);
		
		nouvelAlgorithme.ajouterInstruction(new BlocConditionnel(blocConditionnel.condition, vrai, faux));
	}

	@Override
	public void visit(InstructionAffectation instructionAffectation) {
		nouvelAlgorithme.ajouterInstruction(instructionAffectation);
	}

	@Override
	public void visit(InstructionAffichage instructionAffichage) {
		nouvelAlgorithme.ajouterInstruction(instructionAffichage);
	}


}
