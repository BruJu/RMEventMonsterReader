package fr.bruju.rmeventreader.implementation.monsterlist.manipulation;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Combat;

/**
 * Condition sur l'id d'un combat
 * @author Bruju
 *
 */
public class ConditionOnBattleId implements Condition<Combat> {
	/**
	 * Opérateur de comparaison
	 */
	private Operator operator;
	
	/**
	 * Valeur de droite de comparaison
	 */
	private int rightValue;
	
	/**
	 * Construit une condition sur un combat
	 * tel que id du combat operator rightValue
	 * @param operator L'opérateur de comparaison
	 * @param rightValue La valeur de comparaison
	 */
	public ConditionOnBattleId(Operator operator, int rightValue) {
		this.operator = operator;
		this.rightValue = rightValue;
	}

	@Override
	public void revert() {
		operator = operator.revert();
	}

	@Override
	public boolean filter(Combat combat) {
		return operator.test(combat.getId(), rightValue);
	}
}
