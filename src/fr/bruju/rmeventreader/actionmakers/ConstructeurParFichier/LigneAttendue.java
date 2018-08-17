package fr.bruju.rmeventreader.actionmakers.ConstructeurParFichier;

public class LigneAttendue implements Traitement {
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
	public Object resultat() {
		return null;
	}

}
