package fr.bruju.rmeventreader.implementation.recomposeur;

import fr.bruju.rmeventreader.actionmakers.actionner.AutoActionMaker;
import fr.bruju.rmeventreader.implementation.recomposeur.actionmaker.ComposeurInitial;

public class Recomposition {
	private final String CHEMIN_PARAMETRES = "ressources\\recomposeur\\Parametres.txt";
	private final String CHEMIN_ATTAQUES = "ressources\\recomposeur\\attaques";
	
	
	
	public static void exploiter() {
		
		
		
		ComposeurInitial composeur = new ComposeurInitial(null);
		
		AutoActionMaker autoActionMaker = new AutoActionMaker(composeur, null);
		
		
		
		
	}
}
