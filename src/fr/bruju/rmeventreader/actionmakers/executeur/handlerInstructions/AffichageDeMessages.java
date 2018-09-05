package fr.bruju.rmeventreader.actionmakers.executeur.handlerInstructions;

import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.Ignorance;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ExecEnum;

class AffichageDeMessages implements Remplisseur {
	@Override
	public void remplirMap(Map<Integer, HandlerInstruction> handlers, Map<Integer, HandlerInstructionRetour> classe2) {
		// ShowMessage
		handlers.put(10110, (e, t, c) -> e.getExecMessages().Messages_afficherMessage(c));
		handlers.put(10110, (e, t, c) -> e.getExecMessages().Messages_afficherMessage(c));
		// ShowMessage Followup
		handlers.put(20110, (e, t, c) -> e.getExecMessages().Messages_afficherSuiteMessage(c));
		// MessageOption
		handlers.put(10120, this::changeMessageOptions);
		handlers.put(10130, (e, t, c) -> {});	// TODO changement de portrait
		// Choix multiples
		classe2.put(10140, new HandlerInstructionRetour() {
			@Override
			public boolean traiter(ExecuteurInstructions executeur, int[] parametres, String chaine) {
				return executeur.getExecMessages().SaisieMessages_initierQCM(extraireChoixQCM(parametres[0]));
			}

			@Override
			public Ignorance ignorer() {
				return new Ignorance(10140, 20141);
			}
		});

		handlers.put(20140, (e, p, c) -> e.getExecMessages().SaisieMessages_choixQCM(c, extraireChoixQCM(p[0]+1)));
		
		handlers.put(20141, this::finQCM);
		// Saisie de nombres
		handlers.put(10150, (e, t, c) -> e.getExecMessages().SaisieMessages_saisieNombre(t[1], t[0]));
		
		handlers.put(10740, this::saisieDeNom);
		
		handlers.put(11610, this::appuiTouche);
	}
	
	

	private void appuiTouche(ExecuteurInstructions executeur, int[] parametres, String chaine) {
		int numeroVariable = parametres[0];
		boolean bloquant = parametres[1] == 1;
		int enregistrementTempsMis = parametres.length >= 9 ? (parametres[8] == 1 && bloquant ? -1 : parametres[7] ) : -1;
		
		boolean entree = parametres[3] == 1;
		boolean annuler = parametres[4] == 1;
		if (parametres.length <= 5) {
			executeur.getExecMessages().Messages_appuiTouche(numeroVariable, bloquant, enregistrementTempsMis,
					false, false, false, false, entree, annuler, false, false, false);
			return;
			// TODO : compatiblité
		}
		
		boolean maj = parametres[5] == 1;
		boolean chiffres = parametres[6] == 1;
		boolean symboles = parametres[9] == 1;
		boolean bas = parametres[10] == 1;
		boolean gauche = parametres[11] == 1;
		boolean droite = parametres[12] == 1;
		boolean haut = parametres[13] == 1;
		
		executeur.getExecMessages().Messages_appuiTouche(numeroVariable, bloquant, enregistrementTempsMis,
				haut, droite, bas, gauche, entree, annuler, maj, chiffres, symboles);
	}
	
	private void saisieDeNom(ExecuteurInstructions executeur, int[] parametres, String chaine) {
		int idHeros = parametres[0];
		boolean lettres = parametres[1] == 0;
		boolean afficherNomParDefaut = parametres[2] == 1;
		executeur.getExecMessages().SaisieMessages_SaisieNom(idHeros, lettres, afficherNomParDefaut);
	}
	
	
	/* ===
	 * QCM
	 * === */

	private void finQCM(ExecuteurInstructions executeur, int[] parametres, String chaine) {
		executeur.getExecMessages().SaisieMessages_finQCM();
	}
	

	/* ===
	 * Messages
	 * === */
	
	private void changeMessageOptions(ExecuteurInstructions executeur, int[] parametres, String chaine) {
		executeur.getExecMessages().Messages_modifierOptions(
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
