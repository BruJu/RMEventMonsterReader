package fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme;

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


	@Override
	public void accept(VisiteurDAlgorithme visiteur) {
		visiteur.visit(this);
	}

	@Override
	public boolean estIdentique(InstructionGenerale instructionGenerale) {
		return false;
	}
}
