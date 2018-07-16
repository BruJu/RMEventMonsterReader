package fr.bruju.rmeventreader.actionmakers.actionner;

import fr.bruju.rmeventreader.actionmakers.decrypter.Decrypter;
import fr.bruju.rmeventreader.filereader.ActionOnLine;

/**
 * Cette classe sert de proxy aux instructions afin de ne commencer l'analyse qu'à partir de l'instruction - SCRIPT - si
 * la ligne --- EVENT --- est présente
 * 
 * @author Bruju
 *
 */
public class InstructionReader implements ActionOnLine {
	private final String EVENT_LINE = "--- EVENT ---";
	private final String SCRIPT_LINE = "- SCRIPT -";

	/**
	 * Statuts de lecture
	 */
	private enum Statut {
		Inconnu, Event, Script
	}

	/**
	 * Classe de décryptage des lignes
	 */
	private Decrypter decrypter;

	/**
	 * Construit un lecteur d'instructions
	 * 
	 * @param decrypter
	 */
	public InstructionReader(Decrypter decrypter) {
		this.decrypter = decrypter;
	}

	/**
	 * Statut
	 */
	private Statut statut = Statut.Inconnu;

	@Override
	public void read(String line) {
		if (statut == Statut.Inconnu) {
			if (line.equals(EVENT_LINE)) {
				statut = Statut.Event;
			} else {
				statut = Statut.Script;
			}
		}

		if (statut == Statut.Event) {
			if (line.equals(SCRIPT_LINE)) {
				statut = Statut.Script;
			}
		}

		if (statut == Statut.Script) {
			decrypter.decript(line);
		}
	}

}
