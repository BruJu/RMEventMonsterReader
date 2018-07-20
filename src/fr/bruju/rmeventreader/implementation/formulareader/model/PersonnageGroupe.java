package fr.bruju.rmeventreader.implementation.formulareader.model;

import java.util.Set;

public class PersonnageGroupe implements Personnage {
	private String nom;
	private Set<PersonnageReel> personnages;

	public PersonnageGroupe(String nom, Set<PersonnageReel> personnages) {
		this.nom = nom;
		this.personnages = personnages;
	}
	
	@Override
	public String getNom() {
		return nom;
	}

	@Override
	public Set<PersonnageReel> desunifier() {
		return personnages;
	}

}
