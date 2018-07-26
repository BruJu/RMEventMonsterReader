package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.affichage;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;

public class MaillonSystemOut implements Maillon {

	@Override
	public void traiter(Attaques attaques) {
		attaques.forEach(attaque -> System.out.println(attaque.getChaineAAfficher()));
	}
}
