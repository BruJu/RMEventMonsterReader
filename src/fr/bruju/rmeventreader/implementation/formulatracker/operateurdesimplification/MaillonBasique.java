package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.ConstructeurDeComposantsRecursif;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.Attaques;

public class MaillonBasique extends ConstructeurDeComposantsRecursif implements Maillon {

	@Override
	public void traiter(Attaques attaques) {
		attaques.transformerComposants(this::traiter);
	}

}
