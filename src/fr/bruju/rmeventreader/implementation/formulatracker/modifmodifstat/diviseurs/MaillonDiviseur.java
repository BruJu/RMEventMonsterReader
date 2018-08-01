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
		liste.add(new Diviseur(new DiviseurArme(2)));
		liste.add(new Diviseur(new DiviseurArme(3)));
		liste.add(new Diviseur(new DiviseurArme(4)));
		liste.add(new Diviseur(new DiviseurArme(5)));
		liste.add(new Diviseur(new DiviseurArme(6)));
		liste.add(new Diviseur(new DiviseurArme(7)));
		
		attaques.appliquerDiviseur(liste);
	}

}
