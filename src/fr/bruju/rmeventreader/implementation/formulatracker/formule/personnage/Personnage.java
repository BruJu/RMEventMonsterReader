package fr.bruju.rmeventreader.implementation.formulatracker.formule.personnage;

import java.util.Map;
import java.util.Set;

public interface Personnage {
	
	public Map<String, Statistique> getStatistiques();
	


	public String getNom();

	public Set<PersonnageReel> getPersonnagesReels();
	
}
