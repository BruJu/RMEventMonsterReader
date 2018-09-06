package fr.bruju.rmeventreader.implementationexec.formulatracker.operateurdesimplification.affichage;

import fr.bruju.rmeventreader.implementationexec.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementationexec.formulatracker.operateurdesimplification.Maillon;

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
