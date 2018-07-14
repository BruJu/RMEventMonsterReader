package monsterlist.actionmaker;

import actionner.ActionMakerWithConditionalInterest;
import actionner.Operator;
import actionner.ReturnValue;
import actionner.SwitchChange;
import actionner.SwitchNumber;
import monsterlist.metier.Positions;

public class FinDeCombat implements ActionMakerWithConditionalInterest {
	private static boolean appartient(int element, int[] elements) {
		for (int membre : elements) {
			if (element == membre) {
				return true;
			}
		}
		
		return false;
	}
	
	private final static int[] VARIABLES_IGNOREES = {514, 516, 517};
	private final static int[] VARIABLES_CAPA = Positions.POS_CAPA.ids;

	@Override
	public void condElse() {
		// TODO Auto-generated method stub

	}

	@Override
	public void condEnd() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean caresAboutCondOnSwitch(int number, boolean value) {
		return false;
	}

	@Override
	public boolean caresAboutCondOnVariable(int idVariable, Operator operatorValue, ReturnValue returnValue) {
		if (appartient(idVariable, VARIABLES_IGNOREES)) {
			return true;
		}
		
		if (appartient(idVariable, VARIABLES_CAPA)) {
			return true;
		}
		
		
		return false;
	}

	@Override
	public void changeSwitch(SwitchNumber interrupteur, SwitchChange value) {

	}

	@Override
	public void changeVariable(SwitchNumber variable, Operator operator, ReturnValue returnValue) {

	}

	@Override
	public void condOnSwitch(int number, boolean value) {

	}

	@Override
	public void condOnVariable(int leftOperandValue, Operator operatorValue, ReturnValue returnValue) {

	}

}
