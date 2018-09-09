package fr.bruju.rmeventreader.implementation.monsterlist.autotraitement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.filereader.FileReaderByLine;
import fr.bruju.rmeventreader.filereader.LigneNonReconnueException;
import fr.bruju.rmeventreader.filereader.Recognizer;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Combat;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;

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
	private List<Action> actions;
	/** Base de données de monstres */
	private MonsterDatabase db;
	/** Nom du fichier */
	private String filename;

	
	/**
	 * Crée un correcteur pour la base de données donnée
	 * @param db La base de données à corriger
	 */
	public Correcteur(MonsterDatabase db, String filename) {
		actions = new ArrayList<>();
		actions.add(new ActionAddMonster());
		actions.add(new ActionDeleteMonster());
		actions.add(new ActionDeleteBattle());
		this.filename = filename;
		
		this.db = db;
	}

	@Override
	public void run() {
		try {
			FileReaderByLine.lireLeFichierSansCommentaires(filename, this::read);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Action réalisée à la lecture d'une ligne
	 * @param ligne Ligne lue
	 */
	public void read(String ligne) {
		for (Action action : actions) {
			List<String> arguments = Recognizer.tryPattern(action.getPattern(), ligne);
			
			if (arguments != null) {
				action.faire(db, arguments);
				return;
			}
		}
		
		throw new LigneNonReconnueException(ligne);
	}
	
	/* =================
	 * ACTIONS POSSIBLES
	 * ================= */
	
	/**
	 * Actions réalisables à la lecture d'un pattern
	 */
	private static interface Action {
		/** Donne le pattern reconnu */
		public String getPattern();
		/** Action exécutée à la lecture d'un pattern reconnu */
		public void faire(MonsterDatabase db, List<String> arguments);
	}

	/**
	 * Action réalisée pour ajouter un monstre
	 */
	private static class ActionAddMonster implements Action {
		@Override
		public String getPattern() {
			return "ADDMONSTER _ _ _";
		}

		@Override
		public void faire(MonsterDatabase db, List<String> arguments) {
			int idCombat  = Integer.parseInt(arguments.get(0));
			int idSlot    = Integer.parseInt(arguments.get(1));
			int idMonstre = Integer.parseInt(arguments.get(2));
			
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
		public String getPattern() {
			return "DELETEMONSTER _ _";
		}

		@Override
		public void faire(MonsterDatabase db, List<String> arguments) {
			int idCombat  = Integer.parseInt(arguments.get(0));
			int idSlot    = Integer.parseInt(arguments.get(1));
			
			Combat combat = db.getBattleById(idCombat);			
			combat.remove(idSlot);
		}
	}
	

	/**
	 * Action réalisée pour supprimer un combat
	 */
	private static class ActionDeleteBattle implements Action {
		@Override
		public String getPattern() {
			return "DELETEBATTLE _";
		}

		@Override
		public void faire(MonsterDatabase db, List<String> arguments) {
			int idCombat  = Integer.parseInt(arguments.get(0));
			
			db.removeBattle(idCombat);
		}
	}
}
