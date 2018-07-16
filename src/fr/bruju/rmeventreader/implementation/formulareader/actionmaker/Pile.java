package fr.bruju.rmeventreader.implementation.formulareader.actionmaker;

import java.util.ArrayList;
import java.util.List;

public class Pile {
	public static enum Valeur {
		VRAI,
		FAUX,
		EXPLOREBOTH
	}
	
	List<Valeur> valeurs = new ArrayList<>();
	
	public void empiler(Valeur valeur) {
		valeurs.add(valeur);
	}
	
	public void depiler() {
		valeurs.remove(valeurs.size() - 1);
	}
	
	public boolean possedeUnFaux() {
		for (int i = 0 ; i != valeurs.size() ; i++ ) {
			if (valeurs.get(i) == Valeur.FAUX)
				return true;
		}
		
		return false;
	}
	
	public void inverserSommet() {
		Valeur v = valeurs.get(valeurs.size() - 1);
		
		if (v != Valeur.EXPLOREBOTH) {
			if (v == Valeur.VRAI) {
				v = Valeur.FAUX;
			} else {
				v = Valeur.VRAI;
			}
			
			valeurs.set(valeurs.size() - 1, v);
		}
		
		
	}
}
