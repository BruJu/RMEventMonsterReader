package fr.bruju.rmeventreader.implementation.monsterlist.autotraitement;

import java.util.HashMap;
import java.util.Map;

import fr.bruju.rmeventreader.implementation.monsterlist.metier.Combat;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;
import fr.bruju.rmeventreader.utilitaire.LecteurDeFichiersLigneParLigne;

/**
 * Corrige la liste des monstres en utilisant un fichier
 * <p>
 * 
 * Fichier au format :
 * <br>// Commentaire
 * <br>ADDMONSTER 205 0 135
 * <br>DELETEMONSTER 77 2
 * <br>DELETEMONSTER 78 2
 * <br>DELETEMONSTER 357 2
 * <br>DELETEBATTLE 224
 * 
 * @author Bruju
 *
 */
public class Correcteur implements Runnable {

	/** Liste des actions reconnaissables */
	private Map<String, Action> actions;
	/** Base de données de monstres */
	private MonsterDatabase db;
	/** Nom du fichier */
	private String filename;

	
	/**
	 * Crée un correcteur pour la base de données donnée
	 * @param db La base de données à corriger
	 */
	public Correcteur(MonsterDatabase db, String filename) {
		actions = new HashMap<>();
		actions.put("ADDMONSTER", new ActionAddMonster());
		actions.put("DELETEMONSTER", new ActionDeleteMonster());
		actions.put("DELETEBATTLE", new ActionDeleteBattle());
		
		this.filename = filename;
		this.db = db;
	}

	@Override
	public void run() {
		LecteurDeFichiersLigneParLigne.lectureFichierRessources(filename, this::read);
	}

	/**
	 * Action réalisée à la lecture d'une ligne
	 * @param ligne Ligne lue
	 */
	public void read(String ligne) {
		String[] donnees = ligne.split(" ");
		
		Action action = actions.get(donnees[0]);
		action.faire(db, donnees);
	}
	
	/* =================
	 * ACTIONS POSSIBLES
	 * ================= */
	
	/**
	 * Actions réalisables à la lecture d'un pattern
	 */
	private static interface Action {
		/** Action exécutée à la lecture d'un pattern reconnu */
		public void faire(MonsterDatabase db, String[] donnees);
	}

	
	/**
	 * Action réalisée pour ajouter un monstre
	 */
	private static class ActionAddMonster implements Action {
		@Override
		public void faire(MonsterDatabase db, String[] arguments) {
			int idCombat  = Integer.parseInt(arguments[1]);
			int idSlot    = Integer.parseInt(arguments[2]);
			int idMonstre = Integer.parseInt(arguments[3]);
			
			Combat combat = db.getBattleById(idCombat);
			Monstre monstre = combat.getMonstre(idSlot);
			monstre.setId(idMonstre);
		}
	}

	
	/**
	 * Action réalisée pour supprimer un monstre
	 */
	private static class ActionDeleteMonster implements Action {
		@Override
		public void faire(MonsterDatabase db, String[] arguments) {
			int idCombat  = Integer.parseInt(arguments[1]);
			int idSlot    = Integer.parseInt(arguments[2]);
			
			Combat combat = db.getBattleById(idCombat);			
			combat.remove(idSlot);
		}
	}
	

	/**
	 * Action réalisée pour supprimer un combat
	 */
	private static class ActionDeleteBattle implements Action {
		@Override
		public void faire(MonsterDatabase db, String[] arguments) {
			int idCombat  = Integer.parseInt(arguments[1]);
			
			db.removeBattle(idCombat);
		}
	}
}
