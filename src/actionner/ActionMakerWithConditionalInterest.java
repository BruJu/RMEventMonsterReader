package actionner;

public interface ActionMakerWithConditionalInterest extends ActionMaker {
	public boolean caresAboutCondOnSwitch(int number, boolean value);
	
	public boolean caresAboutCondOnEquippedItem(int heroId, int itemId);
	
	public boolean caresAboutCondOnVariable(int leftOperandValue, Operator operatorValue, ReturnValue returnValue);
	
	public boolean caresAboutCondOnOwnedItem(int itemId);
	
	public boolean caresAboutCondTeamMember(int memberId);

	@Override
	default void notImplementedFeature(String str) {
	}

	@Override
	default void changeSwitch(SwitchNumber interrupteur, SwitchChange value) {
	}

	@Override
	default void changeVariable(SwitchNumber variable, Operator operator, ReturnValue returnValue) {
	}

	@Override
	default void modifyItems(ReturnValue idItem, boolean add, ReturnValue quantity) {
	}

	@Override
	default void condOnSwitch(int number, boolean value) {
	}

	@Override
	default void condOnEquippedItem(int heroId, int itemId) {
	}

	@Override
	default void condOnVariable(int leftOperandValue, Operator operatorValue, ReturnValue returnValue) {
	}

	@Override
	default void condOnOwnedItem(int itemId) {
	}

	@Override
	default void condTeamMember(int memberId) {
	}

	@Override
	default void condElse() {
	}

	@Override
	default void condEnd() {
	}

	@Override
	default void showPicture(int id, String pictureName) {
	}

	@Override
	default void label(int labelNumber) {
	}

	@Override
	default void jumpToLabel(int labelNumber) {
	}
	
	
}
