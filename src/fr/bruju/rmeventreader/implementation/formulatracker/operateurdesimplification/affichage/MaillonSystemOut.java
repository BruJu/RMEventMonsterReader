package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.affichage;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;

/**
 * Affichage sur la sortie standard le résultat
 * 
 * @author Bruju
 *
 */
public class MaillonSystemOut implements Maillon {

	@Override
	public void traiter(Attaques attaques) {
		System.out.println(attaques.getAffichage());
	}
}
