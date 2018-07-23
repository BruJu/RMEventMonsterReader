package fr.bruju.rmeventreader.implementation.formulatracker.contexte.personnage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class PersonnageReel implements Personnage {
	private final String nom;
	private Map<String, Statistique> statistiques;
	
	public PersonnageReel(String nom) {
		this.nom = nom;
		statistiques = new HashMap<>();
	}

	public String getNom() {
		return nom;
	}

	public Map<String, Statistique> getStatistiques() {
		return statistiques;
	}
	
	public void addStatistique(String nom, int position) {
		statistiques.put(nom, new Statistique(this, nom, position));
	}

	@Override
	public Set<PersonnageReel> getPersonnagesReels() {
		Set<PersonnageReel> set = new TreeSet<>();
		set.add(this);
		return set;
	}
	
}
