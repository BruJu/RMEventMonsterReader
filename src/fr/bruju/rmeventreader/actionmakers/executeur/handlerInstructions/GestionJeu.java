package fr.bruju.rmeventreader.actionmakers.executeur.handlerInstructions;

import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.FixeVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurMembre;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ArrierePlanCombat;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Couleur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.EvenementDeplacable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ExecEnum;

@SuppressWarnings("unused")
class GestionJeu implements Remplisseur {
	private Dechiffreur d = Dechiffreur.getInstance();
	
	@Override
	public void remplirMap(Map<Integer, HandlerInstruction> handlers) {
		
		handlers.put(10810, this::teleporter);
		handlers.put(10820, this::memoriserPosition);
		handlers.put(10830, (e, p, s) -> e.Jeu_revenirPosition(p[0], p[1], p[2]));
		handlers.put(10840, (e, p, s) -> e.Jeu_entrerVehicule());
		handlers.put(10850, this::deplacerVehicule);
		handlers.put(10860, this::deplacerEvenement);
		handlers.put(10870, (e, p, s) -> e.Jeu_inverserEvenements(new EvenementDeplacable(p[0]),
				new EvenementDeplacable(p[1])));
		
		handlers.put(10910, this::stockerIdTerrain);
		handlers.put(10920, this::stockerIdEvenement);
		
		handlers.put(11010, this::effacerEcran);
		handlers.put(11020, this::afficherEcran);
		
		handlers.put(11030, this::modifierTonEcran);
	}

	private void modifierTonEcran(ExecuteurInstructions executeur, int[] parametres, String s) {
		Couleur couleur = new Couleur(parametres[0], parametres[1], parametres[2]);
		int saturation = parametres[3];
		int tempsMs = parametres[4];
		boolean pause = parametres[5] == 1;
		
		executeur.Jeu_tonEcran(couleur, saturation, tempsMs, pause);
	}
	
	
	
	private void afficherEcran(ExecuteurInstructions executeur, int[] parametres, String s) {
		if (parametres[0] == -1) {
			executeur.Jeu_afficherEcran(ExecEnum.Transition.DEFAUT);
		} else {
			executeur.Jeu_afficherEcran(ExecEnum.Transition.values()[parametres[0]]);
		}
	}

	private void effacerEcran(ExecuteurInstructions executeur, int[] parametres, String s) {
		if (parametres[0] == -1) {
			executeur.Jeu_cacherEcran(ExecEnum.Transition.DEFAUT);
		} else {
			executeur.Jeu_cacherEcran(ExecEnum.Transition.values()[parametres[0]]);
		}
	}

	private void stockerIdEvenement(ExecuteurInstructions executeur, int[] parametres, String s) {
		FixeVariable x = d.dechiffrerFixeVariable(parametres[0], parametres[1]);
		FixeVariable y = d.dechiffrerFixeVariable(parametres[0], parametres[2]);
		
		executeur.Jeu_stockerIdEvenement(x, y, parametres[3]);
	}
	
	private void stockerIdTerrain(ExecuteurInstructions executeur, int[] parametres, String s) {
		FixeVariable x = d.dechiffrerFixeVariable(parametres[0], parametres[1]);
		FixeVariable y = d.dechiffrerFixeVariable(parametres[0], parametres[2]);
		
		executeur.Jeu_stockerIdTerrain(x, y, parametres[3]);
	}

	private void deplacerEvenement(ExecuteurInstructions executeur, int[] parametres, String s) {
		EvenementDeplacable deplacable = new EvenementDeplacable(parametres[0]);

		FixeVariable x = d.dechiffrerFixeVariable(parametres[1], parametres[2]);
		FixeVariable y = d.dechiffrerFixeVariable(parametres[1], parametres[3]);
		
		executeur.Jeu_deplacerEvenement(deplacable, x, y);
	}
	
	private void deplacerVehicule(ExecuteurInstructions executeur, int[] parametres, String s) {
		ExecEnum.Vehicule vehicule = ExecEnum.Vehicule.values()[parametres[0]];
		FixeVariable z = d.dechiffrerFixeVariable(parametres[1], parametres[2]);
		FixeVariable x = d.dechiffrerFixeVariable(parametres[1], parametres[3]);
		FixeVariable y = d.dechiffrerFixeVariable(parametres[1], parametres[4]);
		executeur.Jeu_deplacerVehicule(vehicule, z, x, y);
	}
	
	
	private void teleporter(ExecuteurInstructions executeur, int[] parametres, String s) {
		executeur.Jeu_teleporter(parametres[0], parametres[1], parametres[2],
				ExecEnum.Direction.values()[parametres[3]]);
	}

	private void memoriserPosition(ExecuteurInstructions executeur, int[] p, String s) {
		executeur.Jeu_memoriserPosition(p[0], p[1], p[2]);
	}
	
	
}
