package fr.bruju.rmeventreader.actionmakers.executeur.handlerInstructions;

import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ExecEnum;

public class AffichageDeMessages {
	
	public void remplirMap(Map<Integer, HandlerInstruction> handlers) {
		// ShowMessage
		handlers.put(10110, (e, t, c) -> e.Messages_afficherMessage(c));
		// ShowMessage Followup
		handlers.put(20110, (e, t, c) -> e.Messages_afficherSuiteMessage(c));
		// MessageOption
		handlers.put(10120, this::changeMessageOptions);
	}
	
	
	private void changeMessageOptions(ExecuteurInstructions executeur, int[] parametres, String chaine) {
		executeur.Messages_modifierOptions(
				parametres[0] == 1,
				extrairePosition(parametres[1]),
				parametres[2] == 1,
				parametres[3] == 0);
	}


	private ExecEnum.Position extrairePosition(int position) {
		switch (position) {
		case 0:
			return ExecEnum.Position.HAUT;
		case 1:
			return ExecEnum.Position.MILIEU;
		case 2:
			return ExecEnum.Position.BAS;
		default:
			return null;
		}
	}
	
	
	
	
	
}
