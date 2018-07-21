package fr.bruju.rmeventreader.implementation.formulatracker.contexte.personnage;

import java.util.Comparator;

public interface Personnage extends Comparator<Personnage> {

	@Override
	default int compare(Personnage p1, Personnage p2) {
		if (p1 instanceof PersonnageReel) {
			if (!(p2 instanceof PersonnageReel)) {
				return -1;
			}
		} else {
			if (p2 instanceof PersonnageReel) {
				return 1;
			}
		}
		
		return p1.getNom().compareTo(p2.getNom());
	}

	public String getNom();
	
}
