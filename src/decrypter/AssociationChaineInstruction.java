package decrypter;

import java.util.ArrayList;
import java.util.List;

import decrypter.convertisseurs.Action;
import decrypter.convertisseurs.IgnoreLine;
import decrypter.convertisseurs.NoAction;
import decrypter.convertisseurs.SetVariable;
import decrypter.convertisseurs.ShowPicture;
import decrypter.convertisseurs.condition.*;
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
		book.add(new IgnoreLine("<>"));
		
		// Instructions non traitées
		book.add(new NoAction("Show Message", "<> Show Message: _"));				// Ne sert pas à l'analyse
		book.add(new NoAction("Message Style", "<> Message Style: £"));				// Ne sert pas à l'analyse
		book.add(new NoAction("Select Face", "<> Select Face:"));					// Ne sert pas à l'analyse
		book.add(new NoAction("Set Event Position", "<> Set Event Location:£"));	// Ne sert pas à l'analyse
		
		book.add(new NoAction("Change Skill", "<> Change Skill:£"));				// Source buggée
		
		book.add(new NoAction("Call Event", "<> Call Event:£"));					// Serait utile mais pas rentable
		
		book.add(new NoAction("Sound Effet", "<> Play Sound Effect£"));				// Intéret mineur
		
		book.add(new NoAction("Change Variable On Screen", "<> Change Variable: _ = _ position on £"));	// Intéret mineur
		
		
		book.add(new NoAction("Set Hero Opacity", "<> Set Hero Opacity:£"));
		
		book.add(new NoAction("Memorize BGM", "<> Memorize BGM"));
		book.add(new NoAction("Commentary", "<> Comment: £"));

		
		// Interrupteurs
		book.add(new ToggleList());
		book.add(new Toggle());
		book.add(new SetSwitchList());
		book.add(new SetSwitch());

		// Changement de variable
		book.add(new SetVariable());
		
		// Conditions
		book.add(new ConditionOnSwitch());
		book.add(new ConditionOnEquippedItem());
		book.add(new ConditionOnVariable());
		book.add(new ElseFork());
		book.add(new EndFork());
		
		book.add(new ShowPicture());
		
		
		// <> Show Picture: #80, combfond-4, (160, 200), Mgn 100%, Tsp 100%/100%
		
		
		return book;
	}

}
