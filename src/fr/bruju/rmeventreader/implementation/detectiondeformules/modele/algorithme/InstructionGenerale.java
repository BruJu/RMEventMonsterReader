package fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme;

public interface InstructionGenerale {

	void append(ListeurDInstructions sb);

	boolean estVide();
	
	
	public void accept(VisiteurDAlgorithme visiteur);

	boolean estIdentique(InstructionGenerale instructionGenerale);
}
