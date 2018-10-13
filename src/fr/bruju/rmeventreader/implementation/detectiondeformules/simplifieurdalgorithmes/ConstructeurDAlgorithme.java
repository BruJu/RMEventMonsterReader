package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffichage;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.Condition;

public interface ConstructeurDAlgorithme {
	public void ajouter(InstructionAffectation affectation);
	
	public void ajouter(InstructionAffichage affichage);
	
	/**
	 * 
	 * @param condition
	 * @return 0 = explorer tout, 1 = explorer vrai, 2 = explorer faux
	 */
	public int commencerCondition(Condition condition);
	
	public void conditionElse() ;
	
	public void conditionFinie();
}
