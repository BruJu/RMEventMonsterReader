package fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme;

import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.visiteurs.VisiteurDAlgorithme;
import fr.bruju.util.IndentedStringBuilder;

/**
 * Une instruction affichant un commentaire
 */
public class InstructionAffichage implements InstructionGenerale {
	/** Le commentaire */
	private final String chaine;

	/**
	 * Crée une instruction dont le but est d'afficher un message
	 * @param chaine Le messaege à afficher
	 */
	public InstructionAffichage(String chaine) {
		this.chaine = chaine;
	}

	@Override
	public void listerTextuellement(IndentedStringBuilder listeur) {
		listeur.append(chaine).ln();
	}

	@Override
	public boolean estVide() {
		return true;
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
