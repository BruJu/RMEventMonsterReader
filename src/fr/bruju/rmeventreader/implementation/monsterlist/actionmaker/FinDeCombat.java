package fr.bruju.rmeventreader.implementation.monsterlist.actionmaker;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;
import fr.bruju.rmeventreader.actionmakers.actionner.ActionMakerDefalse;
import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.Condition;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionEstUnBoss;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionOnMembreStat;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionPassThrought;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Combat;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Positions;
import fr.bruju.rmeventreader.utilitaire.Ensemble;

/**
 * Action maker dont le but est de déterminer les gains totaux d'un combat.
 * 
 * @author Bruju
 *
 */
public class FinDeCombat extends StackedActionMaker<Combat> {
	/* ==========
	 * Constantes
	 * ========== */
	/** Variables avec la position des gains d'expérience */
	private final static int[] VARIABLES_EXP = Positions.POS_EXP.ids;
	/** Variable contenant les gains d'exp totaux */
	private final static int VARIABLE_GAINEXP = 4976;
	/** Switch déclarant un combat de boss */
	private final static int SWITCH_BOSS = 190;

	/** Association numéro de variable - sous action maker */
	private Map<Integer, ActionMaker> associationActionMaker;

	/* ========
	 * Database
	 * ======== */

	/**
	 * Base de données
	 */
	private MonsterDatabase db;

	/**
	 * Construit un action maker dont le but est de déterminer les gains totaux d'un combat
	 * 
	 * @param db La base de données
	 */
	public FinDeCombat(MonsterDatabase db) {
		this.db = db;

		initierAssociationActionMaker();
	}

	/**
	 * Met dans la map les comportements à adopter pour certaines variables
	 */
	private void initierAssociationActionMaker() {
		associationActionMaker = new HashMap<>();

		associationActionMaker.put(514, new ComportementIgnore());
		associationActionMaker.put(516, new ComportementIgnore());
		associationActionMaker.put(517, new ComportementIgnore());

		for (int i = 0; i != Positions.NB_MONSTRES_MAX_PAR_COMBAT; i++) {
			associationActionMaker.put(Positions.POS_CAPA.ids[i], new ComportementCapacite(i));
			associationActionMaker.put(VARIABLES_EXP[i], new ComportementExperience(i));
		}

		associationActionMaker.put(VARIABLE_GAINEXP, new ComportementGlobalExperience());
	}

	@Override
	protected Collection<Combat> getAllElements() {
		return db.extractBattles();
	}

	@Override
	public boolean condOnSwitch(int number, boolean value) {
		if (number != SWITCH_BOSS)
			return false;

		this.conditions.push(new ConditionEstUnBoss(value));

		return true;
	}

	// Instructions déléguées

	@Override
	public boolean condOnVariable(int idVariable, Operator operatorValue, ValeurFixe returnValue) {
		ActionMaker actionMaker = associationActionMaker.get(idVariable);
		return (actionMaker != null) ? actionMaker.condOnVariable(idVariable, operatorValue, returnValue) : false;
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurFixe returnValue) {
		ActionMaker actionMaker = associationActionMaker.get(variable.get());
		if (actionMaker != null) {
			actionMaker.changeVariable(variable, operator, returnValue);
		}
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, Variable returnValue) {
		ActionMaker actionMaker = associationActionMaker.get(variable.get());
		if (actionMaker != null) {
			actionMaker.changeVariable(variable, operator, returnValue);
		}
	}
	/* ==========
	 * Exceptions
	 * ========== */

	/**
	 * Exceptions jetées lorsqu'une erreur est trouvée dans la classe FinDeCombat
	 */
	private static class FinDeCombatException extends RuntimeException {
		private static final long serialVersionUID = 7773889825006166977L;

		public FinDeCombatException(String message) {
			super("Fin de combat : " + message);
		}
	}

	/* =================
	 * Sous Action Maker
	 * ================= */

	/**
	 * Interface action maker avec une implémentation par défaut pour les traitements de conditions
	 */
	private interface ActionMakerAllFalse extends ActionMakerDefalse {
		@Override
		public default void condElse() {
		}

		@Override
		public default void condEnd() {
		}
	}

	/**
	 * Comportement lorsqu'une action concernant une variable ignorée est déclenchée
	 */
	private class ComportementIgnore implements ActionMakerAllFalse {
		@Override
		public boolean condOnVariable(int idVariable, Operator operatorValue, ValeurFixe returnValue) {
			conditions.push(new ConditionPassThrought<Combat>());
			return true;
		}
	}

	/**
	 * Comportement concernant une capacité
	 */
	private class ComportementCapacite implements ActionMakerAllFalse {
		private int positionMonstre;

		public ComportementCapacite(int positionMonstre) {
			this.positionMonstre = positionMonstre;
		}

		@Override
		public boolean condOnVariable(int idVariable, Operator operatorValue, ValeurFixe returnValue) {
			conditions.push(
					new ConditionOnMembreStat(Positions.POS_CAPA, positionMonstre, operatorValue, returnValue.get()));
			return true;
		}

		@Override
		public void changeVariable(Variable variable, Operator operator, ValeurFixe returnValue) {
			if (operator == Operator.AFFECTATION) {
				throw new FinDeCombatException("Affectation brute d'une récompense de capa");
			}

			getElementsFiltres().forEach(battle -> battle.addGainCapa(returnValue.get()));
		}
	}

	/**
	 * Comportement concernant l'expérience d'un monstre
	 */
	private class ComportementExperience implements ActionMakerAllFalse {
		private int positionMonstre;

		public ComportementExperience(int positionMonstre) {
			this.positionMonstre = positionMonstre;
		}

		@Override
		public boolean condOnVariable(int idVariable, Operator operatorValue, ValeurFixe returnValue) {
			conditions.push(
					new ConditionOnMembreStat(Positions.POS_EXP, positionMonstre, operatorValue, returnValue.get()));
			return true;

		}

		@Override
		public void changeVariable(Variable variable, Operator operator, ValeurFixe returnValue) {
			throw new FinDeCombatException("Modification d'une quantité d'exp gagnée");
		}
	}

	/**
	 * Comportement concernant l'expérience totale
	 */
	private class ComportementGlobalExperience implements ActionMakerAllFalse {
		@Override
		public boolean condOnVariable(int idVariable, Operator operatorValue, ValeurFixe returnValue) {
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

		@Override
		public void changeVariable(Variable variable, Operator operator, ValeurFixe returnValue) {
			getElementsFiltres().forEach(c -> c.gainExp = operator.compute(c.gainExp, returnValue.get()));
		}

		@Override
		public void changeVariable(Variable variable, Operator operator, Variable returnValue) {
			int idMonstre = Ensemble.getPosition(returnValue.get(), VARIABLES_EXP);

			if (idMonstre == -1) {
				throw new FinDeCombatException("Modifie un gain d'exp selon une variable qui n'est pas un gain d'exp");
			}

			Function<Combat, Integer> valeurReelle;
			valeurReelle = c -> c.getMonstre(idMonstre) == null ? 0 : c.getMonstre(idMonstre).get(Positions.POS_EXP);

			getElementsFiltres().forEach(c -> c.gainExp = operator.compute(c.gainExp, valeurReelle.apply(c)));
		}

	}

}
