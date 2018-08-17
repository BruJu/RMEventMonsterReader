package fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier;

import fr.bruju.rmeventreader.dictionnaires.header.Monteur;

public interface Traitement<K extends Monteur<?>> {
	public Avancement traiter(String ligne);
	
	public void appliquer(K monteur);
	
	public default boolean skippable() {
		return false;
	}
}
