package fr.bruju.rmeventreader.implementationexec.formulatracker.formule.personnage;

import java.util.Map;
import java.util.Set;

/**
 * Représente un personnage
 * 
 * @author Bruju
 *
 */
public interface Personnage {
	/**
	 * Donne la carte associant nom de statistiques et objet statistiques
	 * @return La carte associant nom de statistiques et objet statistiques
	 */
	public Map<String, Statistique> getStatistiques();

	/**
	 * Donne la carte associant nom de propriétés et objet statistiques
	 * @return La carte associant nom de propriétés et objet statistiques
	 */
	public Map<String, Statistique> getProprietes();
	
	/**
	 * Donne le nom du personnage
	 * @return Le nom du personnage
	 */
	public String getNom();

	/**
	 * Donne un set de personnages réels qui sont représentés par ce personnage
	 * @return L'ensemble des personnages réels qui sont représentés par ce personnage
	 */
	public Set<PersonnageReel> getPersonnagesReels();
}
