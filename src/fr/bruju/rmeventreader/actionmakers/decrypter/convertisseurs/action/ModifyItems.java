package fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.action;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;
import fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.Action;
import fr.bruju.rmeventreader.actionmakers.decrypter.toolbox.ReturnValueIdentifier;
import fr.bruju.rmeventreader.actionmakers.donnees.RightValue;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;

/**
 * Modification des objets possédés
 */
public class ModifyItems implements Action {
	private final String PATTERN = "<> Change Items: _ _ _ of item _";
	
	@Override
	public String getPattern() {
		return PATTERN;
	}

	@Override
	public void faire(ActionMaker actionMaker, List<String> arguments) {
		boolean add = arguments.get(0).equals("Add");
		RightValue quantity = ReturnValueIdentifier.getInstance().identify(arguments.get(1));
		RightValue idItem = ReturnValueIdentifier.getInstance().identify(arguments.get(3));
		
		if (idItem instanceof ValeurFixe) {
			if (quantity instanceof ValeurFixe) {
				actionMaker.modifyItems((ValeurFixe) idItem, add, (ValeurFixe) quantity);
			} else if (quantity instanceof Variable) {
				actionMaker.modifyItems((ValeurFixe) idItem, add, (Variable) quantity);
			} else {
				throw new RuntimeException("ModifyItems : idItem ou quantity n'a pas un type valide");
			}
		} else if (idItem instanceof Variable) {
			if (quantity instanceof ValeurFixe) {
				actionMaker.modifyItems((Variable) idItem, add, (ValeurFixe) quantity);
			} else if (quantity instanceof Variable) {
				actionMaker.modifyItems((Variable) idItem, add, (Variable) quantity);
			} else {
				throw new RuntimeException("ModifyItems : idItem ou quantity n'a pas un type valide");
			}
		} else {
			throw new RuntimeException("ModifyItems : idItem ou quantity n'a pas un type valide");
		}
	}
}
