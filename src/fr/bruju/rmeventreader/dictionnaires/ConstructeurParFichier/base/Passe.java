package fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.base;

import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.Avancement;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.Traitement;
import fr.bruju.rmeventreader.dictionnaires.modele.Monteur;

/**
 * Ignore la prochaine ligne
 * @author Bruju
 *
 * @param <K> Le type de monteur associé
 */
public class Passe<K extends Monteur<?>> implements Traitement<K> {
	@Override
	public Avancement traiter(String ligne) {
		return Avancement.Suivant;
	}

	@Override
	public void appliquer(K monteur) {
	}
	
	@Override
	public boolean skippable() {
		return true;
	}
	
	@Override
	public String toString() {
		return "Skip";
	}
}
