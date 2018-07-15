package fr.bruju.rmeventreader.actionmakers.actionner;

import fr.bruju.rmeventreader.actionmakers.donnees.ReturnValue;
import fr.bruju.rmeventreader.actionmakers.donnees.rework.Pointeur;
import fr.bruju.rmeventreader.actionmakers.donnees.rework.Variable;
import fr.bruju.rmeventreader.actionmakers.donnees.rework.VariablePlage;

/**
 * Fourni une impl�mentation de la gestion des conditions, avec possibilit� d'ignorer
 * des blocs dans des conditions.
 * 
 * Cette classe s'utilise comme un d�corateur
 */
public class ConditionalActionMaker implements ActionMaker {
	private ActionMakerWithConditionalInterest base;
	
	private int niveauDignorance = 0;
	
	private boolean isIgnoring() {
		return niveauDignorance != 0;
	}
	
	
	public ConditionalActionMaker(ActionMakerWithConditionalInterest base) {
		this.base = base;
	}
	

	@Override
	public void condOnSwitch(int number, boolean value) {
		if (isIgnoring() || !base.caresAboutCondOnSwitch(number, value)) {
			niveauDignorance = niveauDignorance + 1;
		} else {
			base.condOnSwitch(number, value);
		}
	}

	@Override
	public void condOnEquippedItem(int heroId, int itemId) {
		if (isIgnoring() || !base.caresAboutCondOnEquippedItem(heroId, itemId)) {
			niveauDignorance = niveauDignorance + 1;
		} else {
			base.condOnEquippedItem(heroId, itemId);;
		}
	}

	@Override
	public void condOnVariable(int leftOperandValue, Operator operatorValue, ReturnValue returnValue) {
		if (isIgnoring() || !base.caresAboutCondOnVariable(leftOperandValue, operatorValue, returnValue)) {
			niveauDignorance = niveauDignorance + 1;
		} else {
			base.condOnVariable(leftOperandValue, operatorValue, returnValue);;
		}
	}

	@Override
	public void condOnOwnedItem(int itemId) {
		if (isIgnoring() || !base.caresAboutCondOnOwnedItem(itemId)) {
			niveauDignorance = niveauDignorance + 1;
		} else {
			base.condOnOwnedItem(itemId);
		}
	}

	@Override
	public void condTeamMember(int memberId) {
		if (isIgnoring() || !base.caresAboutCondTeamMember(memberId)) {
			niveauDignorance = niveauDignorance + 1;
		} else {
			base.condTeamMember(memberId);
		}
	}


	@Override
	public void condEnd() {		
		if (isIgnoring()) {
			niveauDignorance = niveauDignorance - 1;
		} else {
			base.condEnd();
		}
	}


	// D�coration br�te
	
	@Override
	public void condElse() {
		if (isIgnoring()) {
			return;
		}
		
		base.condElse();
	}
	
	@Override
	public void notImplementedFeature(String str) {
		if (isIgnoring()) {
			return;
		}
		
		base.notImplementedFeature(str);
	}

	// Change Switch
	
	@Override
	public void changeSwitch(Variable interrupteur, boolean value) {
		if (isIgnoring()) {
			return;
		}
		
		base.changeSwitch(interrupteur, value);
	}

	@Override
	public void changeSwitch(VariablePlage interrupteurs, boolean value) {
		if (isIgnoring()) {
			return;
		}
		
		base.changeSwitch(interrupteurs, value);
	}
	
	@Override
	public void changeSwitch(Pointeur interrupteur, boolean value) {
		if (isIgnoring()) {
			return;
		}
		
		base.changeSwitch(interrupteur, value);
	}

	// Revert Switch
	
	@Override
	public void revertSwitch(Variable interrupteur) {
		if (isIgnoring()) {
			return;
		}
		
		base.revertSwitch(interrupteur);
	}

	@Override
	public void revertSwitch(VariablePlage interrupteur) {
		if (isIgnoring()) {
			return;
		}
		
		base.revertSwitch(interrupteur);
	}

	@Override
	public void revertSwitch(Pointeur interrupteur) {
		if (isIgnoring()) {
			return;
		}
		
		base.revertSwitch(interrupteur);
	}
	
	// Change Variable
	
	@Override
	public void changeVariable(Variable variable, Operator operator, ReturnValue returnValue) {
		if (isIgnoring()) {
			return;
		}
		
		base.changeVariable(variable, operator, returnValue);
	}

	@Override
	public void changeVariable(VariablePlage variable, Operator operator, ReturnValue returnValue) {
		if (isIgnoring()) {
			return;
		}
		
		base.changeVariable(variable, operator, returnValue);
	}

	@Override
	public void changeVariable(Pointeur variable, Operator operator, ReturnValue returnValue) {
		if (isIgnoring()) {
			return;
		}
		
		base.changeVariable(variable, operator, returnValue);
	}
	
	@Override
	public void modifyItems(ReturnValue idItem, boolean add, ReturnValue quantity) {
		if (isIgnoring()) {
			return;
		}
		
		base.modifyItems(idItem, add, quantity);
	}

	@Override
	public void showPicture(int id, String pictureName) {
		if (isIgnoring()) {
			return;
		}
		
		base.showPicture(id, pictureName);
	}

	@Override
	public void label(int labelNumber) {
		if (isIgnoring()) {
			return;
		}
		
		base.label(labelNumber);
	}

	@Override
	public void jumpToLabel(int labelNumber) {
		if (isIgnoring()) {
			return;
		}
		
		base.jumpToLabel(labelNumber);
	}


	@Override
	public void callMapEvent(int eventNumber, int eventPage) {
		if (isIgnoring()) {
			return;
		}
		
		base.callMapEvent(eventNumber, eventPage);
	}





}
