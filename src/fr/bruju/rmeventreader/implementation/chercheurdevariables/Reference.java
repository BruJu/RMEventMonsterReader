package fr.bruju.rmeventreader.implementation.chercheurdevariables;

/**
 * Référence à un évènement
 * 
 * @author Bruju
 *
 */
public interface Reference extends Comparable<Reference> {
	/** Numéro unique pour l'évènement */
	public long numero();

	/** Chaîne représentant la référence pour l'afficher */
	public String getString();
	
	
	@Override
	public default int compareTo(Reference arg0) {
		return Long.compare(this.numero(), arg0.numero());
	}
}
