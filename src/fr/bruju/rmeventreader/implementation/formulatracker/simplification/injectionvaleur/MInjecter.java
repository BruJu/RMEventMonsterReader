package fr.bruju.rmeventreader.implementation.formulatracker.simplification.injectionvaleur;

import fr.bruju.rmeventreader.implementation.formulatracker.contexte.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.exploitation.Maillon;

public class MInjecter implements Maillon {

	@Override
	public void traiter(Attaques attaques) {
		Injection injection = new Injection();
		injection.remplirAvecFichier("ressources/formulatracker/Injection.txt");
		
		Injecteur injecteur = new Injecteur();

		attaques.transformerComposants(composant -> injecteur.substituer(injection, composant));
	}

}
