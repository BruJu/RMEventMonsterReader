package example;

import actionner.ActionMaker;
import actionner.Operator;
import actionner.SwitchChange;
import actionner.SwitchNumber;

public class Printer implements ActionMaker {

	@Override
	public void changeSwitch(SwitchNumber interrupteur, SwitchChange value) {
		
		if (interrupteur.pointed) {
			throw new UnsupportedOperationException("Not Yet implemented");
		}
		
		int number = interrupteur.numberDebut;
		
		switch (value) {
		case OFF:
			System.out.println("Switch " + number + " = OFF");
			break;
		case ON:
			System.out.println("Switch " + number + " = ON");
			break;
		case REVERSE:
			System.out.println("Switch " + number + " = REVERSE");
			break;
		}
	}


	@Override
	public void changeItem(int number, int value) {
		throw new UnsupportedOperationException("Not Yet implemented");
	}



	@Override
	public void notImplementedFeature(String str) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void condOnSwitch(int number, boolean value) {
		System.out.println("If Switch["+number+"] = " + value);
	}

	@Override
	public void condOnEquippedItem(int heroId, int itemId) {
		System.out.println("If Hero "+heroId+" has " + itemId);
	}


	@Override
	public void changeVariable(SwitchNumber variable, Operator operator, String value) {
		throw new UnsupportedOperationException("Not Yet implemented");
		
	}


}
