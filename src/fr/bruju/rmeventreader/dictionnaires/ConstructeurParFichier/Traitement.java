package fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier;

import fr.bruju.rmeventreader.dictionnaires.header.Monteur;

/**
 * Cette interface est un composant de la chaîne pour monter des objets à partir de fichiers
 * 
 * @author Bruju
 *
 * @param <K> Type de l'objet construit
 */
public interface Traitement<K extends Monteur<?>> {
	/**
	 * Traitemnet à appliquer lors de la lecture d'une ligne
	 * @param ligne La ligne à traiter
	 * @return L'avancement à faire dans la chaîne de traitement
	 */
	public Avancement traiter(String ligne);
	
	/**
	 * Applique les lignes qui ont été traitées par ce traitement au monteur
	 * @param monteur Le monteur
	 */
	public void appliquer(K monteur);
	
	/**
	 * Permet de savoir si ce traitement peut être ignoré ou non
	 * @return Vrai si ignorer ce traitement à la fin ne pose pas de problème
	 */
	public default boolean skippable() {
		return false;
	}
}
