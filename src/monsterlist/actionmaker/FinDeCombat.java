package monsterlist.actionmaker;

import actionner.ActionMakerWithConditionalInterest;
import actionner.Operator;
import actionner.ReturnValue;
import actionner.SwitchChange;
import actionner.SwitchNumber;
import monsterlist.metier.MonsterDatabase;
import monsterlist.metier.Positions;

public class FinDeCombat implements ActionMakerWithConditionalInterest {
	private MonsterDatabase db;
	
	public FinDeCombat(MonsterDatabase baseDeDonnees) {
		db = baseDeDonnees;
	}

	private static boolean appartient(int element, int[] elements) {
		for (int membre : elements) {
			if (element == membre) {
				return true;
			}
		}
		
		return false;
	}
	
	private final static int[] VARIABLES_IGNOREES = {514, 516, 517};
	private final static int[] VARIABLES_CAPA = Positions.POS_CAPA.ids;
	private final static int[] VARIABLES_EXP = Positions.POS_EXP.ids;
	private final static int VARIABLE_GAINEXP = 4976;
	private final static int SWITCH_BOSS = 190;
	
	
	@Override
	public void condElse() {
		throw new FinDeCombatException("Cond else non géré");
	}

	@Override
	public void condEnd() {
		
	}

	@Override
	public boolean caresAboutCondOnSwitch(int number, boolean value) {
		return false;
	}

	@Override
	public boolean caresAboutCondOnVariable(int idVariable, Operator operatorValue, ReturnValue returnValue) {
		if (appartient(idVariable, VARIABLES_IGNOREES)) {
			return true;
		}
		
		if (appartient(idVariable, VARIABLES_CAPA)) {
			return true;
		}
		
		
		return false;
	}

	@Override
	public void changeSwitch(SwitchNumber interrupteur, SwitchChange value) {

	}

	@Override
	public void changeVariable(SwitchNumber variable, Operator operator, ReturnValue returnValue) {
		if (variable.pointed) {
			throw new FinDeCombatException("changeVariable : les variables pointées ne sont pas gérées");
		}
		
		if (appartient(variable.numberDebut, VARIABLES_CAPA)) {
			if (operator == Operator.AFFECTATION) {
				throw new FinDeCombatException("Affectation brute d'une récompense de capa");
			}
			
			db.extractBattles().forEach(battle -> battle.addGainCapa(returnValue.value));
			
			return;
		}
	}

	@Override
	public void condOnSwitch(int number, boolean value) {

	}

	@Override
	public void condOnVariable(int leftOperandValue, Operator operatorValue, ReturnValue returnValue) {

	}

	/* ==========
	 * Exceptions
	 * ========== */
	
	public static class FinDeCombatException extends RuntimeException {
		private static final long serialVersionUID = 7773889825006166977L;
		
		public FinDeCombatException(String message) {
			super("Fin de combat : " + message);
		}
	}
	
}
