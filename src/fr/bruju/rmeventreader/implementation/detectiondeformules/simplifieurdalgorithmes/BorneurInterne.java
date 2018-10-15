package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.BlocConditionnel;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffichage;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.VisiteurDAlgorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Expression;

public class BorneurInterne implements VisiteurDAlgorithme {

	private Expression gauche;
	private Expression droite;
	private boolean resultat = true;

	public BorneurInterne(Expression gauche, Expression droite) {
		this.gauche = gauche;
		this.droite = droite;
	}

	public boolean getResultat() {
		return resultat;
	}

	@Override
	public void visit(BlocConditionnel blocConditionnel) {
		resultat = false;
	}

	@Override
	public void visit(InstructionAffectation instructionAffectation) {
		resultat = instructionAffectation.expression.equals(droite)
				&& instructionAffectation.variableAssignee.equals(gauche);
	}

	@Override
	public void visit(InstructionAffichage instructionAffichage) {
		resultat = false;
	}

}
