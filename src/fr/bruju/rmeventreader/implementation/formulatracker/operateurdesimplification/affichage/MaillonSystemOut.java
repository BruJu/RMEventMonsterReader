package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.affichage;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;

public class MaillonSystemOut implements Maillon {

	@Override
	public void traiter(Attaques attaques) {
		System.out.println(attaques.getAffichage());
	}
}
