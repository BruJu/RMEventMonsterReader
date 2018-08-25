package fr.bruju.rmeventreader.actionmakers.executeur.handlerInstructions;

import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.FixeVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurMembre;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ArrierePlanCombat;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ExecEnum;

@SuppressWarnings("unused")
class GestionJeu implements Remplisseur {
	private Dechiffreur d = Dechiffreur.getInstance();
	
	@Override
	public void remplirMap(Map<Integer, HandlerInstruction> handlers) {
		handlers.put(10810, this::teleporter);
		handlers.put(10820, this::memoriserPosition);

	}

	private void teleporter(ExecuteurInstructions executeur, int[] parametres, String s) {
		executeur.Jeu_teleporter(parametres[0], parametres[1], parametres[2],
				ExecEnum.Direction.values()[parametres[3]]);
	}

	private void memoriserPosition(ExecuteurInstructions executeur, int[] p, String s) {
		executeur.Jeu_memoriserPosition(p[0], p[1], p[2]);
	}
	
	
}
