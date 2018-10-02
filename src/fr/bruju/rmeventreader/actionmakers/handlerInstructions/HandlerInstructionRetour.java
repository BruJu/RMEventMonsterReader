package fr.bruju.rmeventreader.actionmakers.handlerInstructions;

import fr.bruju.rmeventreader.actionmakers.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.controlleur.Ignorance;

public interface HandlerInstructionRetour {
	public boolean traiter(ExecuteurInstructions executeur, int[] parametres, String chaine);
	public Ignorance ignorer();
}
