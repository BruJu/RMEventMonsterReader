package fr.bruju.rmeventreader.actionmakers.handlerInstructions;

import fr.bruju.rmeventreader.actionmakers.controlleur.ExecuteurInstructions;

public interface HandlerInstruction {
	public void traiter(ExecuteurInstructions executeur, int[] parametres, String chaine);
}
