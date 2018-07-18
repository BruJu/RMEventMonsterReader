package fr.bruju.rmeventreader.implementation.monsterlist.manipulation;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Combat;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Positions;

/**
 * Condition sur une statistique d'un monstre
 * 
 * @author Bruju
 *
 */
public class ConditionOnMembreStat implements Condition<Combat> {
	/**
	 * Statistique testée
	 */
	private Positions positionStat;
	
	/**
	 * Numéro du monstre testé
	 */
	private int numeroMonstre;
	
	/**
	 * Opérateur de comparaison
	 */
	private Operator operator;
	
	/**
	 * Valeur de référence
	 */
	private int compareTo;

	/**
	 * Construit une condition sur une statistique d'un monstre 
	 * @param posStat La statistique du monstre
	 * @param position La position du monstre
	 * @param operatorValue L'opérateur de comparaison
	 * @param value La valeur à laquelle la statistique sera comparée à droite de la comparaison
	 */
	public ConditionOnMembreStat(Positions posStat, int position, Operator operatorValue, int value) {
		positionStat = posStat;
		numeroMonstre = position;
		operator = operatorValue;
		compareTo = value;
	}

	@Override
	public void revert() {
		operator = operator.revert();
	}

	@Override
	public boolean filter(Combat combat) {
		int statMonstre;
		
		Monstre monstre = combat.getMonstre(numeroMonstre);
		
		if (monstre == null) {
			statMonstre = 0;
		} else {
			statMonstre = monstre.get(positionStat);
		}
		
		return operator.test(statMonstre, compareTo);
	}
}
