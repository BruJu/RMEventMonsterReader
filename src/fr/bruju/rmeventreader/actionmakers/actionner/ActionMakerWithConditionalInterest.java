package fr.bruju.rmeventreader.actionmakers.actionner;

import fr.bruju.rmeventreader.actionmakers.donnees.Pointeur;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;

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
	
	// TODO : unifier careAbout et condOn. 
	
	public default boolean caresAboutCondOnSwitch(int number, boolean value) {
		return false;
	}
	
	public default boolean caresAboutCondOnEquippedItem(int heroId, int itemId) {
		return false;
	}
	
	public default boolean caresAboutCondOnVariable(int leftOperandValue, Operator operatorValue, Variable returnValue) {
		return false;
	}

	public default boolean caresAboutCondOnVariable(int leftOperandValue, Operator operatorValue, ValeurFixe returnValue) {
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
	default void changeSwitch(Variable interrupteur, boolean value) {
	}


	@Override
	default void changeSwitch(Pointeur interrupteur, boolean value) {
	}
	
	@Override
	default void revertSwitch(Variable interrupteur) {
	}

	@Override
	default void revertSwitch(Pointeur interrupteur) {
	}
	

	@Override
	default void changeVariable(Variable variable, Operator operator, ValeurFixe returnValue) { }

	@Override
	default void changeVariable(Variable variable, Operator operator, ValeurAleatoire returnValue) { }

	@Override
	default void changeVariable(Variable variable, Operator operator, Variable returnValue) { }

	@Override
	default void changeVariable(Variable variable, Operator operator, Pointeur returnValue) { }
	

	@Override
	default void changeVariable(Pointeur variable, Operator operator, ValeurFixe returnValue) { }
	@Override
	default void changeVariable(Pointeur variable, Operator operator, ValeurAleatoire returnValue) { }
	@Override
	default void changeVariable(Pointeur variable, Operator operator, Variable returnValue) { }
	@Override
	default void changeVariable(Pointeur variable, Operator operator, Pointeur returnValue) { }
	
	@Override
	default void modifyItems(ValeurFixe idItem, boolean add, ValeurFixe quantity) { }
	@Override
	default void modifyItems(ValeurFixe idItem, boolean add, Variable quantity) { }
	@Override
	default void modifyItems(Variable idItem, boolean add, ValeurFixe quantity) { }
	@Override
	default void modifyItems(Variable idItem, boolean add, Variable quantity) { }

	@Override
	default void condOnSwitch(int number, boolean value) {
	}

	@Override
	default void condOnEquippedItem(int heroId, int itemId) {
	}

	@Override
	default void condOnVariable(int leftOperandValue, Operator operatorValue, ValeurFixe returnValue) { }
	@Override
	default void condOnVariable(int leftOperandValue, Operator operatorValue, Variable returnValue) { }

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
	
	@Override
	default void callMapEvent(int eventNumber, int eventPage) {
		
	}
	
	
}
