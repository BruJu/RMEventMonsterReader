package fr.bruju.rmeventreader.actionmakers.monsterlist.actionmaker;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.actionner.ReturnValue;
import fr.bruju.rmeventreader.actionmakers.actionner.SwitchNumber;
import fr.bruju.rmeventreader.actionmakers.monsterlist.manipulation.Condition;
import fr.bruju.rmeventreader.actionmakers.monsterlist.manipulation.ConditionOnMembreStat;
import fr.bruju.rmeventreader.actionmakers.monsterlist.manipulation.ConditionPassThrought;
import fr.bruju.rmeventreader.actionmakers.monsterlist.metier.Combat;
import fr.bruju.rmeventreader.actionmakers.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.actionmakers.monsterlist.metier.Positions;

public class FinDeCombat extends StackedActionMaker<Combat> {
	/* ==========
	 * Constantes
	 * ========== */
	private final static int[] VARIABLES_IGNOREES = {514, 516, 517};
	private final static int[] VARIABLES_CAPA = Positions.POS_CAPA.ids;
	private final static int[] VARIABLES_EXP = Positions.POS_EXP.ids;
	private final static int VARIABLE_GAINEXP = 4976;
	private final static int SWITCH_BOSS = 190;
	
	/* ========
	 * Database
	 * ======== */
	
	private MonsterDatabase db;
	
	public FinDeCombat(MonsterDatabase db) {
		this.db = db;
	}
	
	@Override
	protected List<Combat> getAllElements() {
		return db.extractBattles();
	}
	
	
	@Override
	public boolean caresAboutCondOnSwitch(int number, boolean value) {
		return number == SWITCH_BOSS;
	}

	@Override
	public void condOnSwitch(int number, boolean value) {
		this.conditions.push(new Condition<Combat>() {
			private boolean allume = value;
			
			@Override
			public void revert() {
				allume = !allume;
			}

			@Override
			public boolean filter(Combat element) {
				return element.isBossBattle() == allume;
			}
		});
	}
	
	@Override
	public boolean caresAboutCondOnVariable(int idVariable, Operator operatorValue, ReturnValue returnValue) {
		if (appartient(idVariable, VARIABLES_IGNOREES)) {
			return true;
		}
		
		if (appartient(idVariable, VARIABLES_CAPA)) {
			return true;
		}

		if (appartient(idVariable, VARIABLES_EXP)) {
			return true;
		}
		
		if (idVariable == VARIABLE_GAINEXP) {
			return true;
		}
		
		return false;
	}
	@Override
	public void condOnVariable(int idVariable, Operator operatorValue, ReturnValue returnValue) {
		if (appartient(idVariable, VARIABLES_IGNOREES)) {
			conditions.push(new ConditionPassThrought<Combat>());
			return;
		}
		
		if (appartient(idVariable, VARIABLES_CAPA)) {
			conditions.push(
					new ConditionOnMembreStat(
							Positions.POS_CAPA,
							getPosition(idVariable, VARIABLES_CAPA),
							operatorValue,
							returnValue.value
						));
			return;
		}

		if (appartient(idVariable, VARIABLES_EXP)) {
			conditions.push(
					new ConditionOnMembreStat(
							Positions.POS_EXP,
							getPosition(idVariable, VARIABLES_EXP),
							operatorValue,
							returnValue.value
						));
			return;
		}
		
		if (idVariable == VARIABLE_GAINEXP) {
			conditions.push(new Condition<Combat>() {
				Operator operator = operatorValue;
				int value = returnValue.value;

				@Override
				public void revert() {
					operator = operator.revert();
				}

				@Override
				public boolean filter(Combat element) {
					return operator.test(element.gainExp, value);
				}
			});
			return;
		}
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
			
			this.getElementsFiltres().forEach(battle -> battle.addGainCapa(returnValue.value));
			
			return;
		}
		
		if (appartient(variable.numberDebut, VARIABLES_EXP)) {
			throw new FinDeCombatException("Modification d'une quantité d'exp gagnée");
		}
		
		if (variable.numberDebut == VARIABLE_GAINEXP) {
			modificationGainExp(operator, returnValue);
			return;
		}	
	}
	
	
	private void modificationGainExp(Operator operator, ReturnValue returnValue) {
		if (returnValue.type == ReturnValue.Type.POINTER) {
			throw new FinDeCombatException("Modifie un gain d'exp selon un pointeur");
		}
		
		AssociationCombatValeur valeurReelle;
		
		if (returnValue.type == ReturnValue.Type.VALUE) {
			valeurReelle = c -> returnValue.value;
		} else {
			int idMonstre = getPosition(returnValue.value, VARIABLES_EXP);
			
			if (idMonstre == -1) {
				throw new FinDeCombatException("Modifie un gain d'exp selon une variable qui n'est pas un gain d'exp");
			}
			
			valeurReelle = c -> c.getMonstre(idMonstre) == null ? 0 : c.getMonstre(idMonstre).get(Positions.POS_EXP);
		}
		
		getElementsFiltres().forEach(c -> c.gainExp = operator.compute(c.gainExp, valeurReelle.getValue(c)));
	}

	/* ==========
	 * Appartient
	 * ========== */
	
	private static int getPosition(int element, int[] elements) {
		for (int i = 0 ; i != elements.length ; i++) {
			if (elements[i] == element) {
				return i;
			}
		}
		
		return -1;
	}
	
	
	private static boolean appartient(int element, int[] elements) {
		return getPosition(element, elements) != -1;
	}

	/* =========
	 * Aleatoire
	 * ========= */
	
	interface AssociationCombatValeur {
		public int getValue(Combat combat);
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
