package fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.base;

import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.Avancement;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.Traitement;
import fr.bruju.rmeventreader.dictionnaires.header.Monteur;

public class LigneAttendue<K extends Monteur<?>> implements Traitement<K> {
	private String ligneAttendue;
	
	public LigneAttendue(String ligneAttendue) {
		this.ligneAttendue = ligneAttendue;
	}

	@Override
	public Avancement traiter(String ligne) {
		if (ligne.equals(ligneAttendue)) {
			return Avancement.Suivant;
		} else {
			return Avancement.Tuer;
		}
	}

	@Override
	public void appliquer(K monteur) {
	}
	
	@Override
	public String toString() {
		return "LA " + ligneAttendue;
	}
}