package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.executeur;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionGenerale;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.Condition;

public class EtatMemoire {
	public final EtatMemoire pere;
	
	private Condition conditionSeparatrice;
	private EtatMemoire filsGauche;
	private EtatMemoire filsDroit;
	
	private Algorithme algorithme = new Algorithme();
	
	public EtatMemoire() {
		pere = null;
	}

	public EtatMemoire(EtatMemoire pere) {
		this.pere = pere;
	}
	
	
	public EtatMemoire separer(Condition conditionSeparatrice) {
		this.conditionSeparatrice = conditionSeparatrice;
		filsGauche = new EtatMemoire(this);
		filsDroit = new EtatMemoire(this);
		return filsGauche;
	}
	
	public EtatMemoire getFrere() {
		return pere.filsDroit;
	}
	
	public EtatMemoire revenirAuPere() {
		pere.accumulerFils();
		return pere;
	}

	private void accumulerFils() {
		algorithme.ajouterCondition(conditionSeparatrice, filsGauche.getAlgorithme(), filsDroit.getAlgorithme());
		filsGauche = null;
		filsDroit = null;
	}

	public Algorithme getAlgorithme() {
		return algorithme;
	}

	public void nouvelleInstruction(InstructionGenerale instruction) {
		algorithme.ajouterInstruction(instruction);
	}
	

}