package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.inliner;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.ExprVariable;

public enum StatutVariable {
	DEFINIE,
	MORTE;
	
	public static StatutVariable tuer(ExprVariable cle, StatutVariable ancienStatut) {
		return StatutVariable.MORTE;
	}
}
