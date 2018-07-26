package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.maillonssimples;

import java.util.ArrayList;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.FormuleDeDegats;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;

public class MaillonSupprimerCondition implements Maillon {

	@Override
	public void traiter(Attaques attaques) {
		attaques.transformerFormules(f -> new FormuleDeDegats(new ArrayList<>(), f.formule));
	}

}
