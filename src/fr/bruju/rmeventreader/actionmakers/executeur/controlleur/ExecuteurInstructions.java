package fr.bruju.rmeventreader.actionmakers.executeur.controlleur;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ExecEnum.Position;

public interface ExecuteurInstructions {

	public default void Messages_afficherMessage(String chaine) {
	}

	public default void Messages_afficherSuiteMessage(String chaine) {
	}

	public void Messages_modifierOptions(boolean transparent, Position position,
			boolean positionnementAuto, boolean bloquant);
	
	
	
}
