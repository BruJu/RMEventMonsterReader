package decrypter;

import java.util.ArrayList;
import java.util.List;

import decrypter.convertisseurs.Action;
import decrypter.convertisseurs.IgnoreLine;
import decrypter.convertisseurs.JumpTo;
import decrypter.convertisseurs.Label;
import decrypter.convertisseurs.NoAction;
import decrypter.convertisseurs.action.ModifyItems;
import decrypter.convertisseurs.action.SetVariable;
import decrypter.convertisseurs.action.ShowPicture;
import decrypter.convertisseurs.action.interrupteur.*;
import decrypter.convertisseurs.condition.*;

/**
 * Cette classe imp�mente des associations entre pattern � reconna�tre et action � r�aliser
 */
class AssociationChaineInstruction {
	/**
	 * Le pattern � reconna�tre
	 */
	final String pattern;
	
	/**
	 * Action � appeller
	 */
	final Action action;
	
	/**
	 * Cr�e une association entre pattern et action
	 * @param pattern Le pattern � reconna�tre
	 * @param action L'action � d�clencher si le pattern est reconnu
	 */
	AssociationChaineInstruction(String pattern, Action action) {
		this.pattern = pattern;
		this.action = action;
	}
	
	/**
	 * Renvoie la liste des associations pattern - action
	 * @return La liste des associations pattern - action
	 */
	static List<Action> bookMaker() {
		List<Action> book = new ArrayList<>();
		
		book.add(new IgnoreLine("- SCRIPT -"));
		book.add(new IgnoreLine("<>"));
		
		// Instructions non trait�es
		book.add(new NoAction("Show Message", "<> Show Message: _"));				// Ne sert pas � l'analyse
		book.add(new NoAction("Message Style", "<> Message Style: �"));				// Ne sert pas � l'analyse
		book.add(new NoAction("Select Face", "<> Select Face:"));					// Ne sert pas � l'analyse
		book.add(new NoAction("Set Event Position", "<> Set Event Location:�"));	// Ne sert pas � l'analyse
		book.add(new NoAction("Move Event", "<> Move Event:�"));	// Ne sert pas � l'analyse
		book.add(new NoAction("Wait", "<> Wait:�"));	// Ne sert pas � l'analyse
		book.add(new NoAction("Move Picture", "<> Move Picture:�"));	// Ne sert pas � l'analyse
		book.add(new NoAction("Erase Picture", "<> Erase Picture:�"));	// Ne sert pas � l'analyse
		book.add(new NoAction("Play BGM", "<> Play BGM:�"));	// Ne sert pas � l'analyse
		book.add(new NoAction("Weather", "<> Weather Effects: _"));
		book.add(new NoAction("Screen Tone", "<> Set Screen Tone:�"));	// Ne sert pas � l'analyse
		book.add(new NoAction("Wait Until Moved", "<> Wait Until Moved"));
		book.add(new NoAction("Stop All Movement", "<> Stop All Movement"));
		book.add(new NoAction("Key input", "<> Key Input Processing:�"));
		book.add(new NoAction("Delete Event", "<> Delete Event"));
		
		book.add(new NoAction("Change Skill", "<> Change Skill:�"));				// Source bugg�e
		
		book.add(new NoAction("Call Event", "<> Call Event:�"));					// Serait utile mais pas rentable
		
		book.add(new NoAction("Sound Effet", "<> Play Sound Effect�"));				// Int�ret mineur
		
		book.add(new NoAction("Change Variable On Screen", "<> Change Variable: _ = _ position on �"));	// Int�ret mineur
		
		
		book.add(new NoAction("Set Hero Opacity", "<> Set Hero Opacity:�"));
		book.add(new NoAction("Show Screen", "<> Show Screen: �"));
		book.add(new NoAction("Erase Screen", "<> Erase Screen: �"));
		book.add(new NoAction("Change Transition", "<> Change Transition: �"));
		
		book.add(new NoAction("Memorize BGM", "<> Memorize BGM"));
		book.add(new NoAction("Commentary", "<> Comment: �"));

		book.add(new NoAction("Panorama", "<> Change Panorama: _"));

		book.add(new NoAction("Label", "<> Label: _"));					// A impl�menter
		book.add(new NoAction("Jump", "<> Jump To Label: _"));			// A impl�menter
		book.add(new NoAction("Change Money", "<> Change Money: �"));	// A impl�menter
		
		// Interrupteurs
		book.add(new ToggleList());
		book.add(new Toggle());
		book.add(new SetSwitchList());
		book.add(new SetSwitch());

		// Changement de variable
		book.add(new SetVariable());
		book.add(new ModifyItems());
		
		// Conditions
		book.add(new ConditionOnSwitch());
		book.add(new ConditionOnEquippedItem());
		book.add(new ConditionOnVariable());
		book.add(new ConditionOnOwnedItem());
		book.add(new ElseFork());
		book.add(new EndFork());
		
		book.add(new ShowPicture());
		
		book.add(new ConditionOnTeamMember());
		

		book.add(new Label());
		book.add(new JumpTo());
		
		
		return book;
	}

}
