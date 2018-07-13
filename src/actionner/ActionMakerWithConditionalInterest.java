package actionner;

public interface ActionMakerWithConditionalInterest extends ActionMaker {
	public boolean caresAboutCondOnSwitch(int number, boolean value);
	
	public boolean caresAboutCondOnEquippedItem(int heroId, int itemId);
	
	public boolean caresAboutCondOnVariable(int leftOperandValue, Operator operatorValue, ReturnValue returnValue);
	
	public boolean caresAboutCondOnOwnedItem(int itemId);
	
	public boolean caresAboutCondTeamMember(int memberId);
}
