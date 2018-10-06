package fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.operateurdesimplification.simples;

import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.operateurdesimplification.Maillon;

/**
 * Maillon permettant de ne garder qu'une attaque
 * 
 * @author Bruju
 *
 */
public class MaillonFiltreAttaque implements Maillon {
	/** Nom de l'attaque conservée */
	private String attaqueConservee;
	
	/** Construit le maillon en vue de filtrer les attaques n'ayant pas le nom donné */
	public MaillonFiltreAttaque(String string) {
		this.attaqueConservee = string;
	}

	@Override
	public void traiter(Attaques attaques) {
		attaques.filterAttaques(attaque -> attaque.nom.equals(attaqueConservee));
	}

}
