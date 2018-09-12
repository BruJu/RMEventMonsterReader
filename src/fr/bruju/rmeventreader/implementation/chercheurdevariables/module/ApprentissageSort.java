package fr.bruju.rmeventreader.implementation.chercheurdevariables.module;

import java.util.HashSet;
import java.util.Set;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructionsTrue;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ModuleExecEquipe;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.FixeVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ValeurMembre;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.BaseDeRecherche;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.reference.Reference;

/**
 * Base de recherches des variables utilisées
 * @author Bruju
 *
 */
public class ApprentissageSort implements BaseDeRecherche {
	/** Liste des références trouvées */
	private Set<Reference> references = new HashSet<>();
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
	public class Chercheur implements ExecuteurInstructionsTrue, ModuleExecEquipe {
		/** Référence à ajouter */
		private Reference reference;

		/** Crée le chercheur */
		public Chercheur(Reference reference) {
			this.reference = reference;
		}

		@Override
		public ModuleExecEquipe getExecEquipe() {
			return this;
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
