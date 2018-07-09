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
		
		int number = interrupteur.number;
		
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
	public void changeVariable(int number, Operator operator, int value) {
		throw new UnsupportedOperationException("Not Yet implemented");
	}

	@Override
	public void changeItem(int number, int value) {
		throw new UnsupportedOperationException("Not Yet implemented");
	}



	@Override
	public void notImplementedFeature(String str) {
		// TODO Auto-generated method stub
		
	}


}
