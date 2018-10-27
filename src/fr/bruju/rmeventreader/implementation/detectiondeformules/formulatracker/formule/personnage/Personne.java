package fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.formule.personnage;

import java.util.Set;

public interface Personne<T extends VariablesAssociees> {

	public String getNom();
	
	public T getVariablesAssociees();
	
	public Set<Individu<T>> getIndividus();
}
