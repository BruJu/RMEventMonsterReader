package fr.bruju.rmeventreader.implementationexec.chercheurdevariables;

import java.util.HashSet;
import java.util.Set;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructionsTrue;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ModuleExecMedia;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.SonParam;

public class BaseDeRechercheMusique implements BaseDeRecherche {

	private Set<String> musiques = new HashSet<>();

	public BaseDeRechercheMusique() {
	}

	@Override
	public void afficher() {
		musiques.forEach(System.out::println);
	}

	@Override
	public ExecuteurInstructions getExecuteur(Reference ref) {
		return new Chercheur();
	}

	
	public class Chercheur implements ExecuteurInstructionsTrue, ModuleExecMedia {
		@Override
		public ModuleExecMedia getExecMedia() {
			return this;
		}

		@Override
		public void Media_jouerMusique(String nomMusique, int tempsFondu, SonParam parametresMusicaux) {
			musiques.add(nomMusique);
		}
	}
}
