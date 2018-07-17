package fr.bruju.rmeventreader.actionmakers.decrypter;

import fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.Action;
import fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.Commentary;
import fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.IgnoreLine;
import fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.JumpTo;
import fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.Label;
import fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.MapEventAction;
import fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.NoAction;
import fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.action.ModifyItems;
import fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.action.SetVariable;
import fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.action.ShowPicture;
import fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.action.interrupteur.*;
import fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.condition.*;

/**
 * Cette classe impémente des associations entre pattern à reconnaître et action à réaliser
 */
class AssociationChaineInstruction {
	/**
	 * Le pattern à reconnaître
	 */
	final String pattern;

	/**
	 * Action à appeller
	 */
	final Action action;

	/**
	 * Liste des instructions
	 */
	private static Action[] bdd;

	/**
	 * Crée une association entre pattern et action
	 * 
	 * @param pattern Le pattern à reconnaître
	 * @param action L'action à déclencher si le pattern est reconnu
	 */
	AssociationChaineInstruction(String pattern, Action action) {
		this.pattern = pattern;
		this.action = action;
	}

	/**
	 * Renvoie la liste des associations pattern - action
	 * 
	 * @return La liste des associations pattern - action
	 */
	static Action[] bookMaker() {
		if (bdd == null)
			bdd = new Action[] {
					// Lignes vides
					new IgnoreLine("- SCRIPT -"), new IgnoreLine("<>"),

					new MapEventAction(),

					// Modifications d'état
					new Toggle(), new ToggleList(), new SetSwitchList(), new SetSwitch(),

					new NoAction("Change Variable On Screen", "<> Change Variable: _ = _ position on £"),
					new SetVariable(), new ModifyItems(),

					// Conditions
					new ConditionOnSwitch(), new ConditionOnEquippedItem(), new ConditionOnVariable(),
					new ConditionOnOwnedItem(), new ConditionOnOwnedSpell(), new ConditionOnTeamMember(),
					new ElseFork(), new EndFork(),

					// Misc
					new ShowPicture(),

					// Flot de controle
					new Label(), new JumpTo(),

					new Commentary(),

					// Ne servent pas l'analyse
					new NoAction("Show Message", "<> Show Message: _"),
					new NoAction("Message Style", "<> Message Style: £"),
					new NoAction("Select Face", "<> Select Face:"),
					new NoAction("Set Event Position", "<> Set Event Location:£"),
					new NoAction("Move Event", "<> Move Event:£"), new NoAction("Wait", "<> Wait:£"),
					new NoAction("Move Picture", "<> Move Picture:£"),
					new NoAction("Erase Picture", "<> Erase Picture:£"), new NoAction("Play BGM", "<> Play BGM:£"),
					new NoAction("Weather", "<> Weather Effects: _"),
					new NoAction("Screen Tone", "<> Set Screen Tone:£"),
					new NoAction("Wait Until Moved", "<> Wait Until Moved"),
					new NoAction("Stop All Movement", "<> Stop All Movement"),
					new NoAction("Key input", "<> Key Input Processing:£"),
					new NoAction("Delete Event", "<> Delete Event"), new NoAction("Fade out BGM", "<> Fade Out £"),
					new NoAction("Flash Screen", "<> Flash Screen: £"),
					new NoAction("Shake Screen", "<> Shake Screen: £"),
					new NoAction("Flash Event", "<> Flash Event: £"),
					new NoAction("Play Memorized BGM", "<> Play Memorized BGM"),
					new NoAction("Play Movie", "<> Play Movie:£"), new NoAction("Select face", "<> Select Face:£"),
					new NoAction("Show Battle Animation", "<> Show Battle Animation:£"),
					new NoAction("Call Event", "<> Call Event:£"), new NoAction("Sound Effet", "<> Play Sound Effect£"),

					new NoAction("Set Hero Opacity", "<> Set Hero Opacity:£"),
					new NoAction("Show Screen", "<> Show Screen: £"),
					new NoAction("Erase Screen", "<> Erase Screen: £"),
					new NoAction("Change Transition", "<> Change Transition: £"),

					new NoAction("Memorize BGM", "<> Memorize BGM"),

					new NoAction("Panorama", "<> Change Panorama: _"),

					// Source bugee
					new NoAction("Change Skill", "<> Change Skill:£"),

					// A implémenter
					new NoAction("Change Money", "<> Change Money: £"), new NoAction("Loop", "<> Loop"),
					new NoAction("LoopEnd", ": End of loop"), new NoAction("LoopBreak", "<> Break Loop") };

		return bdd;
	}

}
