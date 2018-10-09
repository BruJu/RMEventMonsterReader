package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression;

import java.util.Set;

public interface VariableUtilisee extends Expression {

	VariableInstanciee nouvelleInstance();

	void ajouterVariables(Set<VariableInstanciee> ensemble);

}