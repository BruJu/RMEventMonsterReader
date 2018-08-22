package fr.bruju.rmeventreader.actionmakers.executeur.handlerInstructions;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;

public interface HandlerInstruction {
	public void traiter(ExecuteurInstructions executeur, int[] parametres, String chaine);
}
