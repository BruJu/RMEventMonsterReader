package fr.bruju.rmeventreader.implementation.formulareader.model;

import java.util.Set;

public interface Personnage extends Comparable<Personnage> {

	public String getNom();
	
	public Set<PersonnageReel> desunifier();

	@Override
	default int compareTo(Personnage arg0) {
		return getNom().compareTo(arg0.getNom());
	}
	
}
