package monsterlist;

import java.util.ArrayList;
import java.util.List;

import actionner.ActionMaker;
import actionner.Operator;
import actionner.ReturnValue;
import actionner.SwitchChange;
import actionner.SwitchNumber;

public class MonsterDatabaseMaker implements ActionMaker {
	int niveauConditionnelTraite = 0;
	int niveauConditionnelCourant = 0;
	
	List<Condition> conditionsActuelles = new ArrayList<>();
	
	private void ignoreIf() {
		niveauConditionnelCourant++;
	}
	
	private void treatIf() {
		niveauConditionnelCourant++;
		niveauConditionnelTraite++;
	}
	
	private void endElse() {
		if (isExecutable()) {
			niveauConditionnelCourant--;
			niveauConditionnelTraite--;
			conditionsActuelles.remove(conditionsActuelles.size() - 1);
		} else {
			niveauConditionnelCourant--;
		}
	}
	
	private boolean isExecutable() {
		return niveauConditionnelCourant == niveauConditionnelTraite; 
	}
	
	
	private MonsterDatabase database;
	
	public MonsterDatabaseMaker() {
		database = new MonsterDatabase();
	}
	
	public MonsterDatabase get() {
		return database;
	}
	
	@Override
	public void changeVariable(SwitchNumber variable, Operator operator, ReturnValue returnValue) {
		if (!isExecutable())
			return;
		
		MonsterDatabase.setVariable(database.filter(conditionsActuelles), variable, operator, returnValue);
	}
	
	@Override
	public void condOnSwitch(int number, boolean value) {
		if (value) {
			conditionsActuelles.add(new Condition());
			treatIf();
		} else {
			ignoreIf();
		}
	}

	@Override
	public void condOnEquippedItem(int heroId, int itemId) {
		ignoreIf();
	}

	@Override
	public void condOnVariable(int leftOperandValue, Operator operatorValue, ReturnValue returnValue) {
		if (leftOperandValue == MonsterDatabase.POS_ID_COMBAT) {
			conditionsActuelles.add(new Condition(operatorValue, returnValue.value));
		} else {
			ignoreIf();
		}
	}
	
	
	@Override
	public void condElse() {
		conditionsActuelles.get(conditionsActuelles.size() - 1).revert();
	}

	@Override
	public void condOnOwnedItem(int itemId) {
		ignoreIf();
	}

	@Override
	public void condTeamMember(int memberId) {
		ignoreIf();
	}


	@Override
	public void condEnd() {
		endElse();
	}
	
	
	
	
	
	@Override
	public void notImplementedFeature(String str) {	}

	@Override
	public void changeSwitch(SwitchNumber interrupteur, SwitchChange value) { }


	@Override
	public void changeItem(int number, int value) {	}

	@Override
	public void modifyItems(ReturnValue idItem, boolean add, ReturnValue quantity) { }

	@Override
	public void showPicture(int id, String pictureName) { }

	@Override
	public void label(int labelNumber) { }

	@Override
	public void jumpToLabel(int labelNumber) { }

}
