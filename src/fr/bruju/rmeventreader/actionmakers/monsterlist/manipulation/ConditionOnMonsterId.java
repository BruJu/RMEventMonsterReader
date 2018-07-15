package fr.bruju.rmeventreader.actionmakers.monsterlist.manipulation;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.monsterlist.metier.Monstre;

/**
 * Condition sur les id des monstres et l'id du combat où ils apparraissent
 * @author Bruju
 *
 */
public class ConditionOnMonsterId implements Condition<Monstre> {
	private boolean onMonstre;
	private Operator operator;
	private int value;
	
	public ConditionOnMonsterId(boolean onMonster, Operator operator, int value) {
		this.onMonstre = onMonster;
		this.operator = operator;
		this.value = value;
	}

	@Override
	public void revert() {
		operator = operator.revert();
	}
	
	@Override
	public boolean filter(Monstre monstre) {
		if (onMonstre) {
			return operator.test(monstre.getId(), value);
		} else {
			return operator.test(monstre.getBattleId(), value);
		}
	}
}
