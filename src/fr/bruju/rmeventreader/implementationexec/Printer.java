package fr.bruju.rmeventreader.implementationexec;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ExecEnum.Position;

public class Printer implements ExecuteurInstructions {
	private StringBuilder lignes = new StringBuilder();
	
	public String get() {
		return lignes.toString();
	}
	
	

	@Override
	public void Messages_afficherMessage(String chaine) {
		lignes.append("ShowMessage> ").append(chaine).append("\n");
	}

	@Override
	public void Messages_afficherSuiteMessage(String chaine) {
		lignes.append("ShowMessage: ").append(chaine).append("\n");
	}
	
	@Override
	public void Messages_modifierOptions(boolean transparent, Position position, boolean positionnementAuto,
			boolean bloquant) {
		lignes.append("MessageOptions> ")
		      .append(transparent ? "Transparent " : "Normal ")
		      .append(position).append(" ")
		      .append(positionnementAuto ? "PositionnementAuto " : "Forcé ")
		      .append(bloquant ? "Bloquant" : "NonBloquant")
		      .append("\n");
	}

}
