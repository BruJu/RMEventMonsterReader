package fr.bruju.rmeventreader.implementation.monsterlist;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.actionmakers.decrypter.Recognizer;
import fr.bruju.rmeventreader.filereader.ActionOnLine;
import fr.bruju.rmeventreader.filereader.LigneNonReconnueException;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;

/**
 * 
 * 
 * Fichier au format :
 * // Commentaire
 * ADDMONSTER 205 0 135
 * DELETEMONSTER 77 2
 * DELETEMONSTER 78 2
 * DELETEMONSTER 357 2
 * DELETEBATTLE 224
 * 
 * @author Bruju
 *
 */
public class Correcteur implements ActionOnLine {
	private static interface Action {
		public String getPattern();
		public void faire(MonsterDatabase db, List<String> arguments);
	}
	
	
	private List<Action> actions;
	private MonsterDatabase db;
	
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

	private static class ActionCommentaire implements Action {
		@Override
		public String getPattern() {
			return "//£";
		}

		@Override
		public void faire(MonsterDatabase db, List<String> arguments) {
			
		}
	}

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
			
			db.getBattleById(idCombat).getMonstre(idSlot).setId(idMonstre);
		}
	}

	private static class ActionDeleteMonster implements Action {
		@Override
		public String getPattern() {
			return "DELETEMONSTER _ _";
		}

		@Override
		public void faire(MonsterDatabase db, List<String> arguments) {
			int idCombat  = Integer.parseInt(arguments.get(0));
			int idSlot    = Integer.parseInt(arguments.get(1));
			
			db.getBattleById(idCombat).remove(idSlot);
		}
	}
	

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
