package fr.bruju.rmeventreader.implementation.chercheurdevariables.module;

import java.util.HashSet;
import java.util.Set;

import fr.bruju.rmeventreader.implementation.chercheurdevariables.BaseDeRecherche;
import fr.bruju.rmeventreader.rmdechiffreur.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.rmdechiffreur.modele.SonParam;
import fr.bruju.rmeventreader.rmdechiffreur.reference.Reference;

/**
 * Recherche du nom de toutes les musiques utilisées dans le projet
 * @author Bruju
 *
 */
public class Musique implements BaseDeRecherche {
	/** Ensemble des musiques utilisées */
	private Set<String> musiques = new HashSet<>();

	@Override
	public void afficher() {
		musiques.forEach(System.out::println);
	}

	@Override
	public ExecuteurInstructions getExecuteur(Reference ref) {
		return new Chercheur();
	}

	/**
	 * Chercheur de musiques utilisées
	 * @author Bruju
	 *
	 */
	public class Chercheur implements ExecuteurInstructions {
		@Override
		public void Media_jouerMusique(String nomMusique, int tempsFondu, SonParam parametresMusicaux) {
			musiques.add(nomMusique);
		}
		
		@Override
		public boolean getBooleenParDefaut() {
			return true;
		}
	}
}
