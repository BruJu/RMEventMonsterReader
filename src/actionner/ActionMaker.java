package actionner;

/**
 * Cette classe est appelée lorsqu'une évènement est lu
 *
 */
public interface ActionMaker {
	
	public void changeSwitch(SwitchNumber interrupteur, SwitchChange value);
	
	
	public void changeVariable(SwitchNumber variable, Operator operator, ReturnValue returnValue);
	public void changeItem(int number, int value);
	
	
	public void condOnSwitch(int number, boolean value);
	
	public void notImplementedFeature(String str);


	public void condOnEquippedItem(int heroId, int itemId);


	public void condElse();
	
	public void condEnd();


	public void condOnVariable(int leftOperandValue, Operator operatorValue, ReturnValue returnValue);


	public void showPicture(int id, String pictureName);
}
