package actionner;

/**
 * Cette classe est appel�e lorsqu'une �v�nement est lu
 *
 */
public interface ActionMaker {
	
	public void changeSwitch(SwitchNumber interrupteur, SwitchChange value);
	
	
	public void changeVariable(int number, Operator operator, int value);
	public void changeItem(int number, int value);
	
	
	public void notImplementedFeature(String str);
}
