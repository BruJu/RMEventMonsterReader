package fr.bruju.rmeventreader.actionmakers.executeur.handlerInstructions;

import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.FixeVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurMembre;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ArrierePlanCombat;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Couleur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Deplacement;
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
		handlers.put(11040, this::flashEcran);
		handlers.put(11050, this::tremblementEcran);
		
		handlers.put(11060, this::defilement);
		handlers.put(11070, (e, p, s) -> e.Jeu_modifierMeteo(ExecEnum.Meteo.values()[p[0]],
															ExecEnum.Intensite.values()[p[1]]));
		
		handlers.put(11210, this::afficherAnimation);
		handlers.put(11310, (e, p, s) -> e.Jeu_transparenceHeros(p[0] == 0));
		handlers.put(11320, this::flasherEvenement);
		handlers.put(11330, this::deplacement);
		
		handlers.put(11340, (e,p,s) -> e.Jeu_toutDeplacer());
		handlers.put(11350, (e,p,s) -> e.Jeu_toutStopper());
		
		handlers.put(11410, (e,p,s) -> {if (p[1] == 0) e.Jeu_attendre(p[0]); else e.Jeu_attendreAppuiTouche();});
	
		handlers.put(11710, (e,p,s) -> e.Jeu_modifierChipset(p[0]));
		handlers.put(11720, this::panorama);
		
		handlers.put(11740, (e,p,s) -> e.Jeu_modifierFrequenceRencontres(p[0]));
		
		handlers.put(11750, this::modifierCarreau);
		handlers.put(11810, this::modifierTeleporteur);
		handlers.put(11830, this::pointDeFuite);
		handlers.put(11910, (e,p,s) -> e.Jeu_ouvrirMenuSauvegarde());
		handlers.put(11950, (e,p,s) -> e.Jeu_ouvrirMenu());
		
	}
	
	private void pointDeFuite(ExecuteurInstructions executeur, int[] parametres, String s) {
		int map = parametres[0];
		int x = parametres[1];
		int y = parametres[2];
		
		int switchActive = parametres[3] == 0 ? 0 : parametres[4];
		
		executeur.Jeu_definirPointDeFuite(map, x, y, switchActive);
	}
	
	
	private void modifierTeleporteur(ExecuteurInstructions executeur, int[] parametres, String s) {
		boolean ajouter = parametres[0] == 0;
		
		int map = parametres[1];
		int x = parametres[2];
		int y = parametres[3];
		
		if (ajouter) {
			int switchActive = parametres[4] == 0 ? 0 : parametres[5];
			executeur.Jeu_ajouterTeleporteur(map, x, y, switchActive);
		} else {
			executeur.Jeu_retirerTeleporteur(map, x, y);
		}
	}
	
	private void modifierCarreau(ExecuteurInstructions executeur, int[] parametres, String s) {
		boolean coucheHaute = parametres[0] == 1;
		int remplace = parametres[1];
		int remplacant = parametres[2];
		
		executeur.Jeu_modifierCarreau(coucheHaute, remplace, remplacant);
	}

	private void panorama(ExecuteurInstructions executeur, int[] parametres, String nomPanorama) {
		int defilementHorizontal = defiler(parametres[0], parametres[1], parametres[2]);
		int defilementVertical = defiler(parametres[3], parametres[4], parametres[5]);
		
		executeur.Jeu_changerPanorama(nomPanorama, defilementHorizontal, defilementVertical);
	}
	
	
	
	
	private int defiler(int i, int j, int k) {
		if (i == 0)
			return -1;
		
		if (j == 0)
			return 0;
		
		return k;
	}


	private void deplacement(ExecuteurInstructions executeur, int[] parametres, String s) {
		EvenementDeplacable deplacable = new EvenementDeplacable(parametres[0]);
		int vitesse = parametres[1];
		boolean repeter = parametres[2] == 1;
		boolean ignorerBloquage = parametres[3] == 1;
		
		executeur.Jeu_deplacer(deplacable, vitesse, repeter, ignorerBloquage, new Deplacement());
	}
	
	private void flasherEvenement(ExecuteurInstructions executeur, int[] parametres, String s) {
		Couleur couleur = new Couleur(parametres[1], parametres[2], parametres[3]);
		int tempsDixiemeDeSec = parametres[5];
		int intensite = parametres[4];
		EvenementDeplacable deplacable = new EvenementDeplacable(parametres[0]);
		boolean pause = parametres[6] == 0;
		
		executeur.Jeu_flasherEvenement(deplacable, couleur, intensite, pause, tempsDixiemeDeSec);
	}
	
	private void afficherAnimation(ExecuteurInstructions executeur, int[] parametres, String s) {
		EvenementDeplacable deplacable = new EvenementDeplacable(parametres[1]);
		int numeroAnimation = parametres[0];
		boolean pause = parametres[2] == 1;
		boolean pleinEcran = parametres[3] == 1;
		
		executeur.Jeu_afficherAnimation(deplacable, numeroAnimation, pause, pleinEcran);
	}
	

	private void defilement(ExecuteurInstructions executeur, int[] parametres, String s) {
		if (parametres[0] == 0) {
			executeur.Jeu_defilementBloquer();
			return;
		} else if (parametres[0] == 1) {
			executeur.Jeu_defilementReprendre();
			return;
		}
		
		int vitesse = parametres[3];
		boolean pause = parametres[4] == 1;
		
		if (parametres[0] == 3) {
			executeur.Jeu_defilementRetour(vitesse, pause);
		}
		
		ExecEnum.Direction direction = ExecEnum.Direction.values()[parametres[1]];
		int nombreDeCases = parametres[2];
		
		executeur.Jeu_defilementPonctuel(vitesse, pause, direction, nombreDeCases);
	}
	

	private void tremblementEcran(ExecuteurInstructions executeur, int[] parametres, String s) {
		int force = parametres[0];
		int intensite = parametres[1];
		int temps = parametres[2];
		boolean bloquant = parametres[3] == 1;
		
		if (parametres[4] == 0) {
			executeur.Jeu_tremblementPonctuel(force, intensite, temps, bloquant);
			return;
		}
		
		if (parametres[4] == 1) {
			executeur.Jeu_tremblementCommencer(force, intensite);
			return;
		}
		
		if (parametres[4] == 2) {
			executeur.Jeu_tremblementStop();
			return;
		}
		
	}
	
	private void flashEcran(ExecuteurInstructions executeur, int[] parametres, String s) {
		if (parametres[6] == 2) {
			executeur.Jeu_flashStop();
			return;
		}
		
		Couleur couleur = new Couleur(parametres[0], parametres[1], parametres[2]);
		int intensite = parametres[3];
		int tempsMs = parametres[4];
		boolean pause = parametres[5] == 1;
		boolean flashUnique = parametres[6] == 1;
		
		executeur.Jeu_flashLancer(couleur, intensite, tempsMs, pause, flashUnique);
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
