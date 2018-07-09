package decrypter;

import java.util.ArrayList;
import java.util.List;

import decrypter.convertisseurs.Action;
import decrypter.convertisseurs.NoAction;
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
		
		// == Page 1 ==

		book.add(new NoAction("Show Message", "<> Show Message: _"));
		book.add(new NoAction("Message Style", "<> Message Style: £"));
		book.add(new NoAction("Select Face", "<> Select Face:"));

		// Interrupteurs
		book.add(new ToggleList());
		book.add(new Toggle());
		book.add(new SetSwitchList());
		book.add(new SetSwitch());
		
		

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
