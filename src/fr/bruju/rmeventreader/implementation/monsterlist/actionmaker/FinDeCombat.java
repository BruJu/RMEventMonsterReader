package fr.bruju.rmeventreader.implementation.monsterlist.actionmaker;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.Condition;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionOnMembreStat;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionPassThrought;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Combat;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Positions;

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
	public boolean condOnSwitch(int number, boolean value) {
		if (number != SWITCH_BOSS)
			return false;
		
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
		
		return true; 
	}
	

	@Override
	public boolean condOnVariable(int idVariable, Operator operatorValue, ValeurFixe returnValue) {
		if (appartient(idVariable, VARIABLES_IGNOREES)) {
			conditions.push(new ConditionPassThrought<Combat>());
			return true;
		}
		
		if (appartient(idVariable, VARIABLES_CAPA)) {
			conditions.push(
					new ConditionOnMembreStat(
							Positions.POS_CAPA,
							getPosition(idVariable, VARIABLES_CAPA),
							operatorValue,
							returnValue.get()
						));
			return true;
		}

		if (appartient(idVariable, VARIABLES_EXP)) {
			conditions.push(
					new ConditionOnMembreStat(
							Positions.POS_EXP,
							getPosition(idVariable, VARIABLES_EXP),
							operatorValue,
							returnValue.get()
						));
			return true;
		}
		
		if (idVariable == VARIABLE_GAINEXP) {
			conditions.push(new Condition<Combat>() {
				Operator operator = operatorValue;
				int value = returnValue.get();

				@Override
				public void revert() {
					operator = operator.revert();
				}

				@Override
				public boolean filter(Combat element) {
					return operator.test(element.gainExp, value);
				}
			});
			return true;
		}

		return false;
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurFixe returnValue) {
		
		if (appartient(variable.get(), VARIABLES_CAPA)) {
			if (operator == Operator.AFFECTATION) {
				throw new FinDeCombatException("Affectation brute d'une récompense de capa");
			}
			
			this.getElementsFiltres().forEach(battle -> battle.addGainCapa(returnValue.get()));
			
			return;
		}
		
		if (appartient(variable.get(), VARIABLES_EXP)) {
			throw new FinDeCombatException("Modification d'une quantité d'exp gagnée");
		}
		
		if (variable.get() == VARIABLE_GAINEXP) {
			modificationGainExp(operator, returnValue);
			return;
		}	
	}
	
	@Override
	public void changeVariable(Variable variable, Operator operator, Variable returnValue) {		
		if (variable.get() == VARIABLE_GAINEXP) {
			modificationGainExp(operator, returnValue);
			return;
		}	
	}
	
	
	private void modificationGainExp(Operator operator, Variable returnValue) {
		AssociationCombatValeur valeurReelle;
		

		int idMonstre = getPosition(returnValue.get(), VARIABLES_EXP);
		
		if (idMonstre == -1) {
			throw new FinDeCombatException("Modifie un gain d'exp selon une variable qui n'est pas un gain d'exp");
		}
		
		valeurReelle = c -> c.getMonstre(idMonstre) == null ? 0 : c.getMonstre(idMonstre).get(Positions.POS_EXP);

		
		getElementsFiltres().forEach(c -> c.gainExp = operator.compute(c.gainExp, valeurReelle.getValue(c)));
	}

	private void modificationGainExp(Operator operator, ValeurFixe returnValue) {
		AssociationCombatValeur valeurReelle;
		
		valeurReelle = c -> returnValue.get();

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
