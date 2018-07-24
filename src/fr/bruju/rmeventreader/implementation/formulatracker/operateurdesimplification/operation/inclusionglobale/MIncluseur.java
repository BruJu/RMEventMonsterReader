package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale;

import fr.bruju.rmeventreader.implementation.formulatracker.contexte.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.exploitation.Maillon;

public class MIncluseur implements Maillon {

	@Override
	public void traiter(Attaques attaques) {
		Incluseur incluseur = new Incluseur();
		attaques.apply(incluseur::inclusionGenerale);
	}

}
