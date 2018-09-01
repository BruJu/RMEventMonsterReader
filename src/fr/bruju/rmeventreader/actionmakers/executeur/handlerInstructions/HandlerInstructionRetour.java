package fr.bruju.rmeventreader.actionmakers.executeur.handlerInstructions;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.Ignorance;

public interface HandlerInstructionRetour {
	public boolean traiter(ExecuteurInstructions executeur, int[] parametres, String chaine);
	public Ignorance ignorer();
}
