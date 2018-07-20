package fr.bruju.rmeventreader.implementation.monsterlist.actionmaker;

import java.util.Collection;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionEstUnBoss;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionOnBattleId;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Combat;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Positions;

/**
 * Action Maker qui crée des combats et rempli dedans les statistiques des monstres
 * @author Bruju
 *
 */
public class MonsterDatabaseMaker extends StackedActionMaker<Combat> {
	
	/* ==================
	 * StackedActionMaker
	 * ================== */

	/**
	 * Base de données
	 */
	private MonsterDatabase database;
	
	/**
	 * Construit un MonsterDatabaseMaker
	 * @param monsterDatabase La base de données à remplir
	 */
	public MonsterDatabaseMaker(MonsterDatabase monsterDatabase) {
		database = monsterDatabase;
	}

	@Override
	protected Collection<Combat> getAllElements() {
		return database.extractBattles();
	}
	
	/* =======
	 * Actions
	 * ======= */

	// Switch
	
	@Override
	public boolean condOnSwitch(int number, boolean value) {
		if (number != MonsterDatabase.POS_BOSSBATTLE)
			return false;
		
		conditions.push(new ConditionEstUnBoss(value));

		return true;
	}
	
	@Override
	public void changeSwitch(Variable interrupteur, boolean value) {
		if (!value) {
			return;
		}
		
		int numeroInterrupteur = interrupteur.get();
		
		if (numeroInterrupteur == MonsterDatabase.POS_BOSSBATTLE) {
			MonsterDatabase.setBossBattle(getElementsFiltres());
		} else {
			Integer numeroMonstrePourFossille = Positions.chercherFossile(numeroInterrupteur);
			
			if (numeroMonstrePourFossille == null)
				return;
			
			getElementsFiltres().forEach(combat -> combat.getMonstre(numeroMonstrePourFossille, Operator.AFFECTATION).immuniserAFossile());
			
			
		}
	}

	// Variables
	
	@Override
	public boolean condOnVariable(int leftOperandValue, Operator operatorValue, ValeurFixe returnValue) {
		if (leftOperandValue != MonsterDatabase.POS_ID_COMBAT)
			return false;

		conditions.push(new ConditionOnBattleId(operatorValue, returnValue.get()));
		
		if (operatorValue == Operator.IDENTIQUE) {
			database.addCombat(returnValue.get());
		}
		
		return true;
	}
	

	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurFixe returnValue) {
		MonsterDatabase.setVariable(getElementsFiltres(), variable, operator, returnValue);
	}
	
}
