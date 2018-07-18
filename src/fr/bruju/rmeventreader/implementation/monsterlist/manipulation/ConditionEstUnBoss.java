package fr.bruju.rmeventreader.implementation.monsterlist.manipulation;

import fr.bruju.rmeventreader.implementation.monsterlist.metier.Combat;

/**
 * Condition sur si un combat est un combat de boss
 * @author Bruju
 *
 */
public class ConditionEstUnBoss implements Condition<Combat> {
	/**
	 * Vrai si le combat doit être un combat de boss
	 */
	private boolean v;
	
	/**
	 * Construit une condition sur si le combat doit être un combat de boss ou non
	 * @param estUnCombatDeBoss Vrai si le combat doit être un combat de boss
	 */
	public ConditionEstUnBoss(boolean estUnCombatDeBoss) {
		this.v = estUnCombatDeBoss;
	}
	
	@Override
	public void revert() {
		v = !v;
	}

	@Override
	public boolean filter(Combat element) {
		return element.isBossBattle() == v;
	}
}
