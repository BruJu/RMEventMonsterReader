package fr.bruju.rmeventreader.implementation.chercheurdevariables.module;

import fr.bruju.rmdechiffreur.ExecuteurInstructions;
import fr.bruju.rmdechiffreur.modele.FixeVariable;
import fr.bruju.rmdechiffreur.reference.Reference;
import fr.bruju.rmeventreader.ProjetS;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.BaseDeRecherche;

import java.util.Set;
import java.util.TreeSet;


public class ObjetObtenu implements BaseDeRecherche {
	/** Liste des évènements où la chaîne est utilisée*/
	private Set<Reference> referencesConnues = new TreeSet<>();

	private int idObjet;

	public ObjetObtenu(int idObjet) {
		this.idObjet = idObjet;
	}

	@Override
	public void afficher() {
		System.out.println("= Objet donné : " + ProjetS.PROJET.extraireObjet(idObjet));
		referencesConnues.forEach(reference -> System.out.println(reference.getString()));
	}

	@Override
	public ExecuteurInstructions getExecuteur(Reference ref) {
		return new Chercheur(ref);
	}

	/**
	 * Chercheur de texte dans un évènement
	 * 
	 * @author Bruju
	 *
	 */
	private class Chercheur implements ExecuteurInstructions {
		/** Reference à ajouter */
		private Reference reference;

		/**
		 * Crée un chercheur de texte pour une référence donnée
		 * @param reference La référence à ajouter si le texte est trouvé
		 */
		public Chercheur(Reference reference) {
			this.reference = reference;
		}

		@Override
		public boolean getBooleenParDefaut() {
			return true;
		}

		@Override
		public void Variables_modifierObjets(boolean ajouter, FixeVariable objet, FixeVariable quantite) {
			objet.appliquerFV(fixe -> {
				if (fixe.valeur == idObjet) {
					referencesConnues.add(reference);
				}

				return null;
			}, null);
		}
	}
}
