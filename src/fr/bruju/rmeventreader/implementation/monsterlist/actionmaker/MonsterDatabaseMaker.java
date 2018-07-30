package fr.bruju.rmeventreader.implementation.monsterlist.actionmaker;

import java.util.Collection;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionEstUnBoss;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionOnBattleId;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Combat;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;
import fr.bruju.rmeventreader.utilitaire.Pair;

/**
 * Action Maker qui crée des combats et rempli dedans les statistiques des monstres
 * 
 * @author Bruju
 *
 */
public class MonsterDatabaseMaker extends StackedActionMaker<Combat> {
	/* ==================
	 * StackedActionMaker
	 * ================== */
	
	/** Variable contenant le numéro du combat */
	private final int POS_ID_COMBAT;
	
	/** Interrupteur contenant l'information si c'est un combat de boss */
	private final int POS_BOSSBATTLE;
	
	/**
	 * Base de données
	 */
	private MonsterDatabase database;

	/**
	 * Construit un MonsterDatabaseMaker
	 * 
	 * @param monsterDatabase La base de données à remplir
	 */
	public MonsterDatabaseMaker(MonsterDatabase monsterDatabase) {
		database = monsterDatabase;
		
		POS_ID_COMBAT = database.contexte.getVariable("MonsterDB_IDCombat");
		POS_BOSSBATTLE = database.contexte.getVariable("MonsterDB_BossBattle");
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
		if (number != POS_BOSSBATTLE)
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

		if (numeroInterrupteur == POS_BOSSBATTLE) {
			getElementsFiltres().forEach(combat -> combat.declareBossBattle());
		} else {
			Pair<Integer, String> monstreTouche = database.contexte.getPropriete(numeroInterrupteur);
			
			if (monstreTouche == null) {
				return;
			}

			getElementsFiltres().stream()
					.map(combat -> combat.getMonstre(monstreTouche.getLeft(), Operator.AFFECTATION))
					.forEach(monstre -> monstre.accessBool(Monstre.PROPRIETES).set(monstreTouche.getRight(), true));
		}
	}

	// Variables

	@Override
	public boolean condOnVariable(int leftOperandValue, Operator operatorValue, ValeurFixe returnValue) {
		if (leftOperandValue != POS_ID_COMBAT)
			return false;

		conditions.push(new ConditionOnBattleId(operatorValue, returnValue.get()));

		if (operatorValue == Operator.IDENTIQUE) {
			database.addCombat(returnValue.get());
		}

		return true;
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurFixe valeur) {
		getElementsFiltres().forEach(combat -> combat.applyModificator(variable.get(), operator, valeur.get()));
	}

}
