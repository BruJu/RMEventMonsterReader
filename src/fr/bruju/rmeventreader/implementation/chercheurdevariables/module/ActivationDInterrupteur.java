package fr.bruju.rmeventreader.implementation.chercheurdevariables.module;

import java.util.TreeSet;
import java.util.Set;

import fr.bruju.rmeventreader.actionmakers.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.controlleur.ExtChangeVariable;
import fr.bruju.rmeventreader.actionmakers.modele.Variable;
import fr.bruju.rmeventreader.actionmakers.reference.Reference;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.BaseDeRecherche;

/**
 * Recherche des références qui activent un interrupteur donné
 * 
 * @author Bruju
 *
 */
public class ActivationDInterrupteur implements BaseDeRecherche {
	/** Références activant l'interrupteur */
	private Set<Reference> referencesConnues = new TreeSet<>();
	/** Numéro de l'interrupteur */
	private int idSwitch;

	/**
	 * Crée une base de recherche d'activation du switch donné
	 * @param idSwitch L'interrupteur dont on veut connaître les évènements qui l'activent
	 */
	public ActivationDInterrupteur(int idSwitch) {
		this.idSwitch = idSwitch;
	}

	@Override
	public void afficher() {
		referencesConnues.forEach(reference -> System.out.println(reference.getString()));
	}

	@Override
	public ExecuteurInstructions getExecuteur(Reference ref) {
		return new ChercheurDeOn(ref);
	}

	/**
	 * Exécuteur d'instructions pour une référence
	 * @author Bruju
	 *
	 */
	public class ChercheurDeOn implements ExecuteurInstructions, ExtChangeVariable {
		/** Référence à ajouter si une activation de switch est trouvé */
		private Reference ref;

		/**
		 * Construit un chercheur d'activation de l'interrupteur pour la référence donnée
		 * @param ref La référence
		 */
		public ChercheurDeOn(Reference ref) {
			this.ref = ref;
		}
		
		@Override
		public boolean getBooleenParDefaut() {
			return true;
		}

		@Override
		public void changeSwitch(Variable interrupteur, boolean nouvelleValeur) {
			if (interrupteur.idVariable == idSwitch && nouvelleValeur == true) {
				referencesConnues.add(ref);
			}
		}	
	}
}
