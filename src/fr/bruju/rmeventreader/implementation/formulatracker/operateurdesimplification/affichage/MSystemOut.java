package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.affichage;

import fr.bruju.rmeventreader.implementation.formulatracker.contexte.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.exploitation.Maillon;

public class MSystemOut implements Maillon {

	@Override
	public void traiter(Attaques attaques) {
		attaques.liste.forEach(a -> System.out.println(a.chaineAAfficher));
	}

}
