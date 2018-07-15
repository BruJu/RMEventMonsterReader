package fr.bruju.rmeventreader.actionmakers.actionner;

/**
 * Interface étendant les action maker afin d'être utilisé conjointement avec
 * ConditionalActionMaker.
 * 
 * On implémente en plus des fonctions pour déclarer si on souhaite traiter
 * les instructions dans certaines conditions. Ces fonctions seront utilisées pour
 * tester si un bloc dans une condition doit être passé ou non.
 * 
 * Cette interface produit également des implémentations par défaut ne faisant rien
 * et renvoyant faux. Cela permet de n'avoir que le code important dans les classes
 * se reposant sur cette interface.
 * 
 * condElse et condEnd ne sont pas préimplémentées afin de faire prendre conscience
 * qu'il faut généralement accepter certains types de conditions.
 */
public interface ActionMakerWithConditionalInterest extends ActionMaker {
	public default boolean caresAboutCondOnSwitch(int number, boolean value) {
		return false;
	}
	
	public default boolean caresAboutCondOnEquippedItem(int heroId, int itemId) {
		return false;
	}
	
	public default boolean caresAboutCondOnVariable(int leftOperandValue, Operator operatorValue, ReturnValue returnValue) {
		return false;
	}
	
	public default boolean caresAboutCondOnOwnedItem(int itemId) {
		return false;
	}
	
	public default boolean caresAboutCondTeamMember(int memberId) {
		return false;
	}

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
	default void showPicture(int id, String pictureName) {
	}

	@Override
	default void label(int labelNumber) {
	}

	@Override
	default void jumpToLabel(int labelNumber) {
	}
	
	
}
