package fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.operateurdesimplification.affichage;

import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.operateurdesimplification.Maillon;

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
