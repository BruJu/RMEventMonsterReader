package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.inliner;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.VariableInstanciee;

public enum StatutVariable {
	DEFINIE,
	MORTE;
	
	public static StatutVariable tuer(VariableInstanciee cle, StatutVariable ancienStatut) {
		return StatutVariable.MORTE;
	}
}
