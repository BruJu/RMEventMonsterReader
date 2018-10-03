package fr.bruju.rmeventreader.actionmakers.reference;

/**
 * Référence à un évènement
 * 
 * @author Bruju
 *
 */
public interface Reference extends Comparable<Reference> {
	/** Chaîne représentant la référence pour l'afficher */
	public String getString();
	
	@Override
	public default int compareTo(Reference arg0) {
		int resultat;
		
		if ((resultat = Integer.compare(idCarte(), arg0.idCarte())) != 0) {
			return resultat;
		} else if ((resultat = Integer.compare(idEvenement(), arg0.idEvenement())) != 0) {
			return resultat;
		} else if ((resultat = Integer.compare(idPage(), arg0.idPage())) != 0) {
			return resultat;
		} else {
			return 0;
		}
	}
	
	
	public int idCarte();
	public int idPage();
	public int idEvenement();
}
