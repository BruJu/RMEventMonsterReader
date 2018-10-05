package fr.bruju.rmeventreader.implementation.monsterlist.manipulation;

import fr.bruju.rmdechiffreur.modele.Comparateur;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;

/**
 * Condition sur les id des monstres et l'id du combat où ils apparraissent
 * @author Bruju
 *
 */
public class ConditionOnMonsterId implements Condition<Monstre> {
	/** Si vrai, la condition est sur le monstre. Sinon sur le combat */
	private boolean onMonstre;
	
	/** Opérateur */
	private Comparateur operator;
	
	/** Valeur avec laquelle comparer */
	private int value;
	
	/**
	 * Construit une condition sur l'id du monstre ou du combat où apparait le monstre
	 * @param onMonster Si vrai teste l'id du monstre, si faux l'id du combat dans lequel le monstre apparait
	 * @param comparateur L'opérateur de comparaison
	 * @param value La valeur de référence
	 */
	public ConditionOnMonsterId(boolean onMonster, Comparateur comparateur, int value) {
		this.onMonstre = onMonster;
		this.operator = comparateur;
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
