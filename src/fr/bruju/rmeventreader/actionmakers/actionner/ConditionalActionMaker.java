package fr.bruju.rmeventreader.actionmakers.actionner;

import fr.bruju.rmeventreader.actionmakers.donnees.Pointeur;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.actionmakers.donnees.VariablePlage;

/**
 * Fourni une implémentation de la gestion des conditions, avec possibilité d'ignorer
 * des blocs dans des conditions.
 * 
 * Cette classe s'utilise comme un décorateur
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
	public void condOnVariable(int leftOperandValue, Operator operatorValue, ValeurFixe returnValue) {
		if (isIgnoring() || !base.caresAboutCondOnVariable(leftOperandValue, operatorValue, returnValue)) {
			niveauDignorance = niveauDignorance + 1;
		} else {
			base.condOnVariable(leftOperandValue, operatorValue, returnValue);;
		}
	}

	@Override
	public void condOnVariable(int leftOperandValue, Operator operatorValue, Variable returnValue) {
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


	// Décoration brûte
	
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
	public void changeVariable(Variable variable, Operator operator, ValeurFixe returnValue) {
		if (isIgnoring()) {
			return;
		}
		
		base.changeVariable(variable, operator, returnValue);
	}

	@Override
	public void changeVariable(VariablePlage variable, Operator operator, ValeurFixe returnValue) {
		if (isIgnoring()) {
			return;
		}
		
		base.changeVariable(variable, operator, returnValue);
	}

	@Override
	public void changeVariable(Pointeur variable, Operator operator, ValeurFixe returnValue) {
		if (isIgnoring()) {
			return;
		}
		
		base.changeVariable(variable, operator, returnValue);
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurAleatoire returnValue) {
		if (isIgnoring()) {
			return;
		}
		
		base.changeVariable(variable, operator, returnValue);
	}

	@Override
	public void changeVariable(VariablePlage variable, Operator operator, ValeurAleatoire returnValue) {
		if (isIgnoring()) {
			return;
		}
		
		base.changeVariable(variable, operator, returnValue);
	}

	@Override
	public void changeVariable(Pointeur variable, Operator operator, ValeurAleatoire returnValue) {
		if (isIgnoring()) {
			return;
		}
		
		base.changeVariable(variable, operator, returnValue);
	}
	@Override
	public void changeVariable(Variable variable, Operator operator, Variable returnValue) {
		if (isIgnoring()) {
			return;
		}
		
		base.changeVariable(variable, operator, returnValue);
	}

	@Override
	public void changeVariable(VariablePlage variable, Operator operator, Variable returnValue) {
		if (isIgnoring()) {
			return;
		}
		
		base.changeVariable(variable, operator, returnValue);
	}

	@Override
	public void changeVariable(Pointeur variable, Operator operator, Variable returnValue) {
		if (isIgnoring()) {
			return;
		}
		
		base.changeVariable(variable, operator, returnValue);
	}
	@Override
	public void changeVariable(Variable variable, Operator operator, Pointeur returnValue) {
		if (isIgnoring()) {
			return;
		}
		
		base.changeVariable(variable, operator, returnValue);
	}

	@Override
	public void changeVariable(VariablePlage variable, Operator operator, Pointeur returnValue) {
		if (isIgnoring()) {
			return;
		}
		
		base.changeVariable(variable, operator, returnValue);
	}

	@Override
	public void changeVariable(Pointeur variable, Operator operator, Pointeur returnValue) {
		if (isIgnoring()) {
			return;
		}
		
		base.changeVariable(variable, operator, returnValue);
	}
	@Override
	public void modifyItems(ValeurFixe idItem, boolean add, ValeurFixe quantity) {
		if (isIgnoring()) {
			return;
		}
		
		base.modifyItems(idItem, add, quantity);
	}

	@Override
	public void modifyItems(ValeurFixe idItem, boolean add, Variable quantity) {
		if (isIgnoring()) {
			return;
		}
		
		base.modifyItems(idItem, add, quantity);
	}
	@Override
	public void modifyItems(Variable idItem, boolean add, ValeurFixe quantity) {
		if (isIgnoring()) {
			return;
		}
		
		base.modifyItems(idItem, add, quantity);
	}
	@Override
	public void modifyItems(Variable idItem, boolean add, Variable quantity) {
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
