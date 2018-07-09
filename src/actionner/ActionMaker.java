package actionner;

/**
 * Cette classe est appelée lorsqu'une évènement est lu
 *
 */
public interface ActionMaker {
	
	public void changeSwitch(SwitchNumber interrupteur, SwitchChange value);
	
	
	public void changeVariable(SwitchNumber variable, Operator operator, String value);
	public void changeItem(int number, int value);
	
	
	public void condOnSwitch(int number, boolean value);
	
	public void notImplementedFeature(String str);


	public void condOnEquippedItem(int heroId, int itemId);
}
