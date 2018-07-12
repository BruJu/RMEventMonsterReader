package monsterlist;

import java.util.ArrayList;
import java.util.List;

import actionner.Operator;
import monsterlist.MonsterDatabase.Combat;


// This implementation is actually horrible performance wise
public class Condition {
	private boolean isActiv;
	
	private Operator operator;
	private int rightValue;
	
	public Condition() {
		isActiv = false;
	}
	
	public Condition(Operator operator, int rightValue) {
		isActiv = true;
		
		this.operator = operator;
		this.rightValue = rightValue;
	}
	
	public void revert() {
		if (!isActiv) {
			return;
		}
		
		operator = operator.revert();
	}
	
	public List<Combat> filter(List<Combat> liste) {
		if (!isActiv)
			return liste;
		
		List<Combat> newCombatList = new ArrayList<>();
		
		for (Combat combat : liste) {
			if (operator.test(combat.id, rightValue)) {
				newCombatList.add(combat);
			}
		}
		
		return newCombatList;
	}
	
	
}