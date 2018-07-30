package fr.bruju.rmeventreader.implementation.monsterlist.actionmaker;

import java.util.Collection;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionOnBattleId;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionPassThrought;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Combat;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;

public class ExtracteurDeFond extends StackedActionMaker<Combat> {
	/* ==================
	 * StackedActionMaker
	 * ================== */

	/** Numéro de la variable contenant le fond */
	private final int VARIABLE_ID_FOND;
	/** Numéro de la variable contenant l'id du combat */
	private final int VARIABLE_IDCOMBAT;
	
	/** Condition sur un switch ignoré */
	private final int SWITCH_IGNORE1;
	/** Condition sur un switch ignoré */
	private final int SWITCH_IGNORE2;
	
	/**	Base de données de monstre */
	private MonsterDatabase bdd;

	/**
	 * Instancie le faiseur d'action avec la base de données à compléter
	 * 
	 * @param database La base de données à compléter
	 */
	public ExtracteurDeFond(MonsterDatabase database) {
		this.bdd = database;

		VARIABLE_IDCOMBAT = database.contexte.getVariable("MonsterDB_IDCombat");
		VARIABLE_ID_FOND  = database.contexte.getVariable("MonsterDB_FondCombat");
		SWITCH_IGNORE1  = database.contexte.getVariable("ExtacteurFond_IgnoreSwitch1");
		SWITCH_IGNORE2  = database.contexte.getVariable("ExtacteurFond_IgnoreSwitch2");
	}
	
	@Override
	protected Collection<Combat> getAllElements() {
		return bdd.extractBattles();
	}

	@Override
	public boolean condOnVariable(int leftOperandValue, Operator operatorValue, ValeurFixe returnValue) {
		if (leftOperandValue == VARIABLE_IDCOMBAT) {
			conditions.push(new ConditionOnBattleId(operatorValue, returnValue.get()));
		} else {
			conditions.push(new ConditionPassThrought<>());
		}
		
		return true;
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurFixe returnValue) {
		if (variable.idVariable != VARIABLE_ID_FOND) {
			return;
		}
		
		getElementsFiltres().forEach(c -> c.addFond(returnValue.valeur));
	}

	@Override
	public boolean condOnSwitch(int number, boolean value) {
		if (number == this.SWITCH_IGNORE1 || number == this.SWITCH_IGNORE2) {
			return false;
		}
		
		conditions.push(new ConditionPassThrought<>());
		return true;
	}
	
	
	
}
