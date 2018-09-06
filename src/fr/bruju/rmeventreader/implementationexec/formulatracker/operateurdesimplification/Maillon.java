package fr.bruju.rmeventreader.implementationexec.formulatracker.operateurdesimplification;

import fr.bruju.rmeventreader.implementationexec.formulatracker.formule.attaques.Attaques;

/**
 * Un maillon est un élément qui est exécuté pour transformer la liste des attaques.
 * 
 * @author Bruju
 *
 */
public interface Maillon {
	/**
	 * Fonction de modification de la liste des attaques
	 * @param attaques La base de données d'attaques à modifier
	 */
	public void traiter(Attaques attaques);
}
