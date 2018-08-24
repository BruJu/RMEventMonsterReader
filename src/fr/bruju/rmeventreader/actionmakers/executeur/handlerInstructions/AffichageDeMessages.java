package fr.bruju.rmeventreader.actionmakers.executeur.handlerInstructions;

import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ExecEnum;

class AffichageDeMessages implements Remplisseur {
	@Override
	public void remplirMap(Map<Integer, HandlerInstruction> handlers) {
		// ShowMessage
		handlers.put(10110, (e, t, c) -> e.Messages_afficherMessage(c));
		// ShowMessage Followup
		handlers.put(20110, (e, t, c) -> e.Messages_afficherSuiteMessage(c));
		// MessageOption
		handlers.put(10120, this::changeMessageOptions);
		// Choix multiples
		handlers.put(10140, this::initialisationQCM);
		handlers.put(20140, this::choixQCM);
		handlers.put(20141, this::finQCM);
		// Saisie de nombres
		handlers.put(10150, (e, t, c) -> e.SaisieMessages_saisieNombre(t[1], t[0]));
	}
	
	/* ===
	 * QCM
	 * === */
	
	private void initialisationQCM(ExecuteurInstructions executeur, int[] parametres, String chaine) {
		executeur.SaisieMessages_initierQCM(extraireChoixQCM(parametres[0]));
	}

	private void choixQCM(ExecuteurInstructions executeur, int[] parametres, String chaine) {
		executeur.SaisieMessages_choixQCM(chaine, extraireChoixQCM(parametres[0]+1));
	}

	private void finQCM(ExecuteurInstructions executeur, int[] parametres, String chaine) {
		executeur.SaisieMessages_finQCM();
	}
	

	/* ===
	 * Messages
	 * === */
	
	private void changeMessageOptions(ExecuteurInstructions executeur, int[] parametres, String chaine) {
		executeur.Messages_modifierOptions(
				parametres[0] == 1,
				extrairePosition(parametres[1]),
				parametres[2] == 1,
				parametres[3] == 0);
	}


	/* ==========
	 * Utilitaire
	 * ========== */
	
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
	
	private ExecEnum.ChoixQCM extraireChoixQCM(int position) {
		return ExecEnum.ChoixQCM.values()[position];
	}
	
	
	
}
