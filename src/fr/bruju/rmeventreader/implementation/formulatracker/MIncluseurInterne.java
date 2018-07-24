package fr.bruju.rmeventreader.implementation.formulatracker;

import fr.bruju.rmeventreader.implementation.formulatracker.contexte.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.exploitation.Maillon;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.Incluseur;

public class MIncluseurInterne implements Maillon {

	@Override
	public void traiter(Attaques attaques) {
		Incluseur incluseur = new Incluseur();
		
		attaques.apply(incluseur::inclureV);
	}

}
