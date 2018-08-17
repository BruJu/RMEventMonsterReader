package fr.bruju.rmeventreader.actionmakers.ConstructeurParFichier;

import fr.bruju.rmeventreader.dictionnaires.header.Monteur;

public class Passe<K extends Monteur<?>> implements Traitement<K> {
	@Override
	public Avancement traiter(String ligne) {
		return Avancement.Suivant;
	}

	@Override
	public void appliquer(K monteur) {
	}
}
