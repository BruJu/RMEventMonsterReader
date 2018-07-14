package monsterlist.manipulation;

import actionner.Operator;
import monsterlist.metier.Combat;
import monsterlist.metier.Monstre;
import monsterlist.metier.Positions;

public class ConditionOnMembreStat implements Condition<Combat> {
	private Positions positionStat;
	private int numeroMonstre;
	private Operator operator;
	private int compareTo;

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
