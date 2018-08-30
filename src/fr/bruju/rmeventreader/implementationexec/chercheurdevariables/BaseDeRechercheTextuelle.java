package fr.bruju.rmeventreader.implementationexec.chercheurdevariables;

import java.util.HashSet;
import java.util.Set;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;

public class BaseDeRechercheTextuelle implements BaseDeRecherche {

	private Set<Reference> referencesConnues = new HashSet<>();
	private String chaine;

	public BaseDeRechercheTextuelle(String string) {
		this.chaine = string;
	}

	@Override
	public void afficher() {
		referencesConnues.forEach(reference -> System.out.println(reference.getString()));
	}

	@Override
	public ExecuteurInstructions getExecuteur(Reference ref) {
		return new ChercheurTexte(ref, referencesConnues, chaine);
	}

}
