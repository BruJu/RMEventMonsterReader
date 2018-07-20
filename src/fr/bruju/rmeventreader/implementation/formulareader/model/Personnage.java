package fr.bruju.rmeventreader.implementation.formulareader.model;

import java.util.Set;

public interface Personnage {

	public String getNom();
	
	public Set<PersonnageReel> desunifier();	
}
