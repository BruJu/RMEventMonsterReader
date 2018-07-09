package decrypter;

import java.util.ArrayList;
import java.util.List;

import decrypter.convertisseurs.Action;
import decrypter.convertisseurs.IgnoreLine;
import decrypter.convertisseurs.NoAction;
import decrypter.convertisseurs.SetVariable;
import decrypter.convertisseurs.condition.ConditionOnEquippedItem;
import decrypter.convertisseurs.condition.ConditionOnSwitch;
import decrypter.convertisseurs.interrupteur.*;

class AssociationChaineInstruction {
	final String pattern;
	final Action action;
	
	AssociationChaineInstruction(String pattern, Action action) {
		this.pattern = pattern;
		this.action = action;
	}
	
	
	static List<Action> bookMaker() {
		List<Action> book = new ArrayList<>();
		
		book.add(new IgnoreLine("- SCRIPT -"));
		
		book.add(new NoAction("Show Message", "<> Show Message: _"));
		book.add(new NoAction("Message Style", "<> Message Style: £"));
		book.add(new NoAction("Select Face", "<> Select Face:"));
		
		book.add(new NoAction("Set Event Position", "<> Set Event Location:£"));
		
		// Interrupteurs
		book.add(new ToggleList());
		book.add(new Toggle());
		book.add(new SetSwitchList());
		book.add(new SetSwitch());
		
		book.add(new ConditionOnSwitch());
		book.add(new ConditionOnEquippedItem());
		
		book.add(new SetVariable());
		
		

		/*
		 *  <> Change Variable: [1] = 0
			<> Change Variable: [1] *= 0
			<> Change Variable: [1] Mod= 0
			<> Change Variable: [1] = V[1]
			<> Change Variable: [1] = Random [0-50]
			<> Change Variable: [1] = V[1]
			<> Change Variable: [1] = V[V[4]]
			<> Change Variable: [1-50] = V[V[4]]
			<> Change Variable: [V[1]] = V[V[4]]
		 */
		
		return book;
	}

}
