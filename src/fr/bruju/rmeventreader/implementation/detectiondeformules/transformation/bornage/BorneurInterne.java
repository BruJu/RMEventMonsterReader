package fr.bruju.rmeventreader.implementation.detectiondeformules.transformation.bornage;

import fr.bruju.rmdechiffreur.modele.Comparateur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.*;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.condition.ConditionVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.Borne;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.NombreAleatoire;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.visiteurs.VisiteurDAlgorithme;

/**
 * Visiteur renvoyant vrai si la dernière instruction d'affectation visitée possède les mêmes termes que ceux passés
 * dans le constructeur
 * 
 * @author Bruju
 *
 */
public class BorneurInterne implements VisiteurDAlgorithme {
	private final ConditionVariable condition;
	private final boolean estBorneMin;
	private InstructionAffectation instruction = null;
	private boolean dejaUtilise = false;


	public BorneurInterne(ConditionVariable condition) {
		this.condition = condition;

		Comparateur comparateur = condition.comparateur;
		this.estBorneMin = comparateur == Comparateur.INF || comparateur == Comparateur.INFEGAL;
	}

	@Override
	public void visit(InstructionAffectation instructionAffectation) {
		if (dejaUtilise || instruction != null) {
			dejaUtilise = true;
			instruction = null;
			return;
		}

		if (!instructionAffectation.variableAssignee.equals(condition.gauche)) {
			dejaUtilise = true;
			return;
		}

		Borne borne = null;

		if (instructionAffectation.expression.equals(condition.droite)) {
			borne = new Borne(condition.gauche, condition.droite, estBorneMin);
		} else {
			Integer evalMinCondition = condition.droite.evaluerMinimum();
			Integer evalMinAffectee = instructionAffectation.expression.evaluerMinimum();

			if (evalMinCondition != null && evalMinAffectee != null) {
				Integer evalMaxCondition = condition.droite.evaluerMaximum();
				Integer evalMaxAffectee = instructionAffectation.expression.evaluerMaximum();

				// TODO : si a <= 0 ; a = 1~5 est actuellement interprété comme a = min(a, 0~5) au lieu de min(a, 1~5)
				NombreAleatoire aleatoire = null;

				if (estBorneMin) {
					if (evalMaxCondition.equals(evalMinAffectee) || evalMaxCondition.equals(evalMinAffectee - 1)) {
						aleatoire = new NombreAleatoire(evalMinCondition, evalMaxAffectee);
					}
				} else {
					if (evalMinCondition.equals(evalMaxAffectee) || evalMinCondition.equals(evalMaxAffectee + 1)) {
						aleatoire = new NombreAleatoire(evalMinAffectee, evalMaxCondition);
					}
				}

				if (aleatoire != null) {
					borne = new Borne(condition.gauche, aleatoire, estBorneMin);
				}
			}
		}

		if (borne != null) {
			instruction = new InstructionAffectation(instructionAffectation.variableAssignee, borne);
		} else {
			dejaUtilise = true;
		}
	}
	
	@Override
	public void visit(BlocConditionnel blocConditionnel) {
		dejaUtilise = true;
	}

	@Override
	public void visit(InstructionAffichage instructionAffichage) {
		dejaUtilise = true;
	}

	public boolean completer(BlocConditionnel blocConditionnel, Algorithme nouvelAlgorithme) {
		visit(blocConditionnel.siVrai);

		if (dejaUtilise || instruction == null) {
			return false;
		} else {
			nouvelAlgorithme.ajouterInstruction(instruction);
			return true;
		}
	}
}
