package fr.bruju.rmeventreader.implementation.chercheurdevariables.module;

import java.util.HashSet;
import java.util.Set;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructionsTrue;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ModuleExecMessages;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.BaseDeRecherche;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.reference.Reference;

/**
 * Recherche une chaîne dans tous les dialogues du jeu
 * @author Bruju
 *
 */
public class Texte implements BaseDeRecherche {
	/** Liste des évènements où la chaîne est utilisée*/
	private Set<Reference> referencesConnues = new HashSet<>();
	/** Chaîne à rechercher */
	private String chaine;

	/**
	 * Crée une base de recherche pour du texte
	 * @param string La chaîne à rechercher
	 */
	public Texte(String string) {
		this.chaine = string;
	}

	@Override
	public void afficher() {
		referencesConnues.forEach(reference -> System.out.println(reference.getString()));
	}

	@Override
	public ExecuteurInstructions getExecuteur(Reference ref) {
		return new ChercheurTexte(ref);
	}

	/**
	 * Chercheur de texte dans un évènement
	 * 
	 * @author Bruju
	 *
	 */
	public class ChercheurTexte implements ExecuteurInstructionsTrue, ModuleExecMessages {
		/** Reference à ajouter */
		private Reference reference;

		/**
		 * Crée un chercheur de texte pour une référence donnée
		 * @param reference La référence à ajouter si le texte est trouvé
		 */
		public ChercheurTexte(Reference reference) {
			this.reference = reference;
		}

		@Override
		public ModuleExecMessages getExecMessages() {
			return this;
		}

		@Override
		public boolean getBooleenParDefaut() {
			return true;
		}

		@Override
		public void Messages_afficherMessage(String message) {
			if (message.contains(chaine))
				referencesConnues.add(reference);
		}

		@Override
		public void Messages_afficherSuiteMessage(String message) {
			if (message.contains(chaine))
				referencesConnues.add(reference);
		}
	}
}
