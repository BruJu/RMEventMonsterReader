package fr.bruju.rmeventreader.implementation.formulatracker.modifmodifstat.diviseurs;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;

public class MaillonDiviseur implements Maillon {

	@Override
	public void traiter(Attaques attaques) {
		List<Diviseur> liste = new ArrayList<>();
		liste.add(new Diviseur(new DiviseurArme(1)));
		
		attaques.appliquerDiviseur(liste);
	}

}
