package fr.bruju.rmeventreader.implementation.formulatracker.modifmodifstat.diviseurs;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;

public class MaillonDiviseur implements Maillon {

	@Override
	public void traiter(Attaques attaques) {
		attaques.appliquerDiviseur(new Diviseur(new DiviseurArme(1)));
		
	}

}
