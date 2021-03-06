package fr.bruju.rmeventreader.implementation.chercheurdevariables.module;

import java.util.TreeSet;
import java.util.Set;

import fr.bruju.rmdechiffreur.ExecuteurInstructions;
import fr.bruju.rmdechiffreur.reference.Reference;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.BaseDeRecherche;

/**
 * Recherche une chaîne dans tous les dialogues du jeu
 * @author Bruju
 *
 */
public class Texte implements BaseDeRecherche {
	/** Liste des évènements où la chaîne est utilisée*/
	private Set<Reference> referencesConnues = new TreeSet<>();
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
	private class ChercheurTexte implements ExecuteurInstructions {
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
