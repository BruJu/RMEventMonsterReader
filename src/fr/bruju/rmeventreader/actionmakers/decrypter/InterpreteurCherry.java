package fr.bruju.rmeventreader.actionmakers.decrypter;

import java.io.File;
import java.io.IOException;
import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;
import fr.bruju.rmeventreader.actionmakers.actionner.Interpreter;
import fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.Action;
import fr.bruju.rmeventreader.filereader.ActionOnLine;
import fr.bruju.rmeventreader.filereader.FileReaderByLine;
import fr.bruju.rmeventreader.filereader.LigneNonReconnueException;
import fr.bruju.rmeventreader.filereader.Recognizer;

/**
 * Classe permettant de déchiffrer des instructions RPG Maker 2003 et d'activer les fonctions de l'actionMaker donné.
 */
public class InterpreteurCherry implements Interpreter {
	/** Liste des pattern à déchiffrer */
	private static Action[] patterns = AssociationChaineInstruction.bookMaker();

	/** Gestionnaire d'action */
	private ActionMaker actionMaker;

	/**
	 * Crée un interpréteur avec l'actionMaker donné
	 * 
	 * @param actionMaker L'objet qui traitera les actions déchiffrées
	 */
	public InterpreteurCherry(ActionMaker actionMaker) {
		this.actionMaker = actionMaker;
	}

	/**
	 * Déchiffre un fichier
	 * 
	 * @param file Le fichier à déchiffrer
	 * @throws IOException
	 */
	@Override
	public void inputFile(File file) throws IOException {
		FileReaderByLine.lireLeFichier(file, new InstructionReader(this));
	}

	/**
	 * Déchiffre une ligne et appelle l'action associée
	 * 
	 * @param line La ligne à déchiffrer
	 */
	public void decript(String line) {
		for (Action pattern : patterns) {
			List<String> argumentsReconnus = Recognizer.tryPattern(pattern.getPattern(), line);

			if (argumentsReconnus != null) {
				pattern.faire(actionMaker, argumentsReconnus);
				return;
			}
		}

		throw new LigneNonReconnueException(line);
	}

	/**
	 * Cette classe sert de proxy aux instructions afin de ne commencer l'analyse qu'à partir de l'instruction - SCRIPT
	 * - si la ligne --- EVENT --- est présente
	 * 
	 * @author Bruju
	 *
	 */
	public static class InstructionReader implements ActionOnLine {
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
		private InterpreteurCherry decrypter;

		/**
		 * Construit un lecteur d'instructions
		 * 
		 * @param decrypter
		 */
		public InstructionReader(InterpreteurCherry decrypter) {
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
}
