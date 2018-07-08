package actionner;

/**
 * Cette classe est appelée lorsqu'une évènement est lu
 *
 */
public interface ActionMaker {
	
	public void changeSwitch(int number, SwitchChange value);
	public void changeVariable(int number, Operator operator, int value);
	public void changeItem(int number, int value);
	
}
