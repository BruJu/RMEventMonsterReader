package fr.bruju.rmeventreader.implementation.chercheurdevariables.module;

import java.util.TreeSet;
import java.util.Set;

import fr.bruju.rmdechiffreur.ExecuteurInstructions;
import fr.bruju.rmdechiffreur.modele.FixeVariable;
import fr.bruju.rmdechiffreur.modele.ValeurMembre;
import fr.bruju.rmdechiffreur.reference.Reference;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.BaseDeRecherche;

/**
 * Base de recherches des variables utilisées
 * @author Bruju
 *
 */
public class ApprentissageSort implements BaseDeRecherche {
	/** Liste des références trouvées */
	private Set<Reference> references = new TreeSet<>();
	/** Id du héros */
	public final int idHeros;
	/** Id du sort */
	public final int idSort;

	/**
	 * Crée une base de recherche de l'apprentissage d'un sort spécifiquement par le héros donné
	 * @param idHeros ID du héros
	 * @param idSort ID du sort
	 */
	public ApprentissageSort(int idHeros, int idSort) {
		this.idHeros = idHeros;
		this.idSort = idSort;
	}

	@Override
	public void afficher() {
		references.forEach(r -> System.out.println(r.getString()));
	}

	@Override
	public ExecuteurInstructions getExecuteur(Reference ref) {
		return new Chercheur(ref);
	}
	
	/** Chercheur de l'apprentissage du sort */
	private class Chercheur implements ExecuteurInstructions {
		/** Référence à ajouter */
		private Reference reference;

		/** Crée le chercheur */
		public Chercheur(Reference reference) {
			this.reference = reference;
		}

		@Override
		public boolean getBooleenParDefaut() {
			return true;
		}
		
		@Override
		public void Equipe_modifierCompetence(ValeurMembre cible, boolean ajouter, FixeVariable sort) {
			// La compraison avec == TRUE est utile parce que trois valeurs sont possibles (true, false et null)
			if ((cible.appliquerMembre(null, f -> f.valeur == idHeros, null) == Boolean.TRUE)
					&& (sort.appliquerFV(f -> f.valeur == idSort, null)) == Boolean.TRUE) {
				references.add(reference);
			}
		}
	}
}
