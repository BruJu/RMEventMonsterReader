package fr.bruju.rmeventreader.implementation.monsterlist;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.filereader.ActionOnLine;
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
public class Correcteur implements ActionOnLine {
	/**
	 * Actions réalisables à la lecture d'un pattern
	 */
	private static interface Action {
		/** Donne le pattern reconnu */
		public String getPattern();
		/** Action exécutée à la lecture d'un pattern reconnu */
		public void faire(MonsterDatabase db, List<String> arguments);
	}
	
	/** Liste des actions reconnaissables */
	private List<Action> actions;
	/** Base de données de monstres */
	private MonsterDatabase db;
	
	/**
	 * Crée un correcteur pour la base de données donnée
	 * @param db La base de données à corriger
	 */
	public Correcteur(MonsterDatabase db) {
		actions = new ArrayList<>();
		actions.add(new ActionCommentaire());
		actions.add(new ActionAddMonster());
		actions.add(new ActionDeleteMonster());
		actions.add(new ActionDeleteBattle());
		
		this.db = db;
	}

	@Override
	public void read(String line) {
		for (Action action : actions) {
			List<String> arguments = Recognizer.tryPattern(action.getPattern(), line);
			
			if (arguments != null) {
				action.faire(db, arguments);
				return;
			}
		}
		
		throw new LigneNonReconnueException(line);
	}
	
	// Actions possibles

	/**
	 * Action réalisée lors d'un commentaire
	 */
	private static class ActionCommentaire implements Action {
		@Override
		public String getPattern() {
			return "//£";
		}

		@Override
		public void faire(MonsterDatabase db, List<String> arguments) {
			
		}
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
