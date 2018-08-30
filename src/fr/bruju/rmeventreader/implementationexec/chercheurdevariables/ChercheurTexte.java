package fr.bruju.rmeventreader.implementationexec.chercheurdevariables;

import java.util.Set;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;

public class ChercheurTexte implements ExecuteurInstructions {
	
	private Reference reference;
	
	private Set<Reference> references;

	private String chaine;

	public ChercheurTexte(Reference reference, Set<Reference> references, String chaine) {
		this.reference = reference;
		this.references = references;
		this.chaine = chaine;
	}

	@Override
	public void Messages_afficherMessage(String chaine) {
		if (chaine.contains(this.chaine))
			references.add(reference);
	}

	@Override
	public void Messages_afficherSuiteMessage(String chaine) {
		if (chaine.contains(this.chaine))
			references.add(reference);
	}
	
	
	
	
	
}
