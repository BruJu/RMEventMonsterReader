package fr.bruju.rmeventreader.actionmakers.controlleur;

/**
 * Un traiteur est une interface donnant le moyen d'exécuter une méthode selon des paramètres et une chaîne. Si la
 * méthode de traitement renvoie faux, cela signifie que le bloc souhaite 
 * 
 * @author Bruju
 *
 */
interface Traiteur {	
	public Ignorance executer(ExecuteurInstructions executeur, int[] parametres, String chaine);
}
