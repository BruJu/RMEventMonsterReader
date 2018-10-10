package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme;

public interface InstructionGenerale {

	void append(ListeurDInstructions sb);

	boolean estVide();
	
	
	public void accept(VisiteurDAlgorithme visiteur);
}
