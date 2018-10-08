package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme;

public class InstructionAffichage implements InstructionGenerale {
	private final String chaine;
	
	
	
	
	public InstructionAffichage(String chaine) {
		this.chaine = chaine;
	}

	@Override
	public void append(ListeurDInstructions sb) {
		sb.append(chaine).ln();
	}

	@Override
	public boolean estVide() {
		return false;
	}

}
