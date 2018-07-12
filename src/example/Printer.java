package example;

import actionner.ActionMaker;
import actionner.Operator;
import actionner.ReturnValue;
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
	public void notImplementedFeature(String str) {
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
	public void changeVariable(SwitchNumber variable, Operator operator, ReturnValue value) {
		System.out.println("V[" + getRepresentation(variable) + "] " + getRepresentation(operator) +" "+ getRepresentation(value));
	}
	
	
	
	
	
	
	
	
	
	
	private String getRepresentation(SwitchNumber variable) {
		String s = "";
		
		if (variable.numberDebut == variable.numberFin) {
			s = Integer.toString(variable.numberDebut);
		} else {
			s = Integer.toString(variable.numberDebut) + "-" + Integer.toString(variable.numberFin);
		}
		
		if (variable.pointed) {
			s = "V[" + s + "]";
		}
		
		return s;
	}

	private String getRepresentation(Operator operator) {
		switch (operator) {
		case AFFECTATION:
			return "=";
		case DIVIDE:
			return "/=";
		case MINUS:
			return "-=";
		case MODULO:
			return "%=";
		case PLUS:
			return "+=";
		case TIMES:
			return "*=";
		case DIFFERENT:
			return "!=";
		case IDENTIQUE:
			return "==";
		case INF:
			return "<";
		case INFEGAL:
			return "<=";
		case SUP:
			return ">";
		case SUPEGAL:
			return ">=";
		}
		
		return null;
	}
	
	private String getRepresentation(ReturnValue value) {
		switch (value.type) {
		case POINTER:
			return "V[V[" + value.value +"]]";
		case VALUE:
			if (value.value == value.borneMax) {
				return Integer.toString(value.value);
			} else {
				return Integer.toString(value.value) + "~" + Integer.toString(value.borneMax);
			}
		case VARIABLE:
			return "V[" + value.value +"]";
		}
		
		return null;
	}


	@Override
	public void condElse() {
		System.out.println("Else");
	}


	@Override
	public void condEnd() {
		System.out.println("End If");
	}


	@Override
	public void condOnVariable(int leftOperandValue, Operator operatorValue, ReturnValue returnValue) {
		System.out.println("If V[" + leftOperandValue + "] " + getRepresentation(operatorValue) + " " + getRepresentation(returnValue));
	}


	@Override
	public void showPicture(int id, String pictureName) {
		System.out.println("Show " + id + " = " + pictureName);
	}


	@Override
	public void condTeamMember(int memberId) {
		System.out.println("If Hero #" + memberId + " is in the team");
	}


	@Override
	public void condOnOwnedItem(int itemId) {
		System.out.println("If Item #" + itemId + " is owned");
	}


	@Override
	public void modifyItems(ReturnValue idItem, boolean add, ReturnValue quantity) {
		String operator = add ? "Got " : "Lose ";
		
		System.out.println(operator + getRepresentation(quantity) + " of " + getRepresentation(idItem));
	}


	@Override
	public void label(int labelNumber) {
		System.out.println("Label " + labelNumber);
	}


	@Override
	public void jumpToLabel(int labelNumber) {
		System.out.println("Goto " + labelNumber);
	}

}
