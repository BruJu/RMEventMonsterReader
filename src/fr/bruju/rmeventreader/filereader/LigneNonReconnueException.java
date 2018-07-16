package fr.bruju.rmeventreader.filereader;

/**
 * Exception jetée lorsqu'une ligne n'est pas reconnue
 */
public class LigneNonReconnueException extends RuntimeException {
	/**
	 * Un serial id unique
	 */
	private static final long serialVersionUID = -6801007137729905515L;
	
	/**
	 * Déclare une LigneNonReconnueException avec la ligne donnée
	 * @param ligne La ligne qui n'a pas pu être reconnue
	 */
	public LigneNonReconnueException(String ligne) {
		super("LigneNonReconnueException : " + ligne);
	}
}