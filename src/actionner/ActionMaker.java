package actionner;

/**
 * Cette classe est appelée lorsqu'une évènement est lu
 *
 */
public interface ActionMaker {

	/*
	 * Not implemented
	 */
	public void notImplementedFeature(String str);
	
	/*
	 * Change of value
	 */
	
	public void changeSwitch(SwitchNumber interrupteur, SwitchChange value);
	public void changeVariable(SwitchNumber variable, Operator operator, ReturnValue returnValue);
	public void changeItem(int number, int value);
	public void modifyItems(ReturnValue idItem, boolean add, ReturnValue quantity);
	
	
	/*
	 * Conditions
	 */
	
	public void condOnSwitch(int number, boolean value);
	public void condOnEquippedItem(int heroId, int itemId);
	public void condOnVariable(int leftOperandValue, Operator operatorValue, ReturnValue returnValue);
	public void condOnOwnedItem(int itemId);
	public void condTeamMember(int memberId);


	public void condElse();
	public void condEnd();


	/*
	 * Misc.
	 */
	public void showPicture(int id, String pictureName);

	public void label(int labelNumber);
	public void jumpToLabel(int labelNumber);
}
