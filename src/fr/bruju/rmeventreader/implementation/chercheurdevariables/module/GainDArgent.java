package fr.bruju.rmeventreader.implementation.chercheurdevariables.module;

import fr.bruju.rmdechiffreur.ExecuteurInstructions;
import fr.bruju.rmdechiffreur.modele.FixeVariable;
import fr.bruju.rmdechiffreur.reference.Reference;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.BaseDeRecherche;
import fr.bruju.util.MapsUtils;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Recherche les gains d'argent
 */
public class GainDArgent implements BaseDeRecherche {
	private Map<Reference, Set<Integer>> gainsConnus = new TreeMap<>();

	@Override
	public void afficher() {

		gainsConnus.forEach((reference, gains) -> {
			StringBuilder sb = new StringBuilder();

			sb.append(reference.getString()).append(" :");

			for (Integer gain : gains) {
				sb.append(" ").append(gain);
			}

			System.out.println(sb.toString());
		});
	}

	@Override
	public ExecuteurInstructions getExecuteur(Reference ref) {
		return new Chercheur(ref);
	}

	private class Chercheur implements ExecuteurInstructions {
		private Reference ref;

		public Chercheur(Reference ref) {
			this.ref = ref;
		}
		
		@Override
		public boolean getBooleenParDefaut() {
			return true;
		}

		@Override
		public void Variables_modifierArgent(boolean ajouter, FixeVariable quantite) {
			quantite.appliquerFV(fixe -> {
				if (ajouter) {
					MapsUtils.ajouterElementDansSet(gainsConnus, ref, fixe.valeur);
				} else {
					MapsUtils.ajouterElementDansSet(gainsConnus, ref, -fixe.valeur);
				}
				return null;
			}, null);
		}
	}
}
