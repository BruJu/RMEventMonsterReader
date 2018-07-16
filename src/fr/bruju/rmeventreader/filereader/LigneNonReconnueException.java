package fr.bruju.rmeventreader.filereader;

/**
 * Exception jet�e lorsqu'une ligne n'est pas reconnue
 */
public class LigneNonReconnueException extends RuntimeException {
	/**
	 * Un serial id unique
	 */
	private static final long serialVersionUID = -6801007137729905515L;
	
	/**
	 * D�clare une LigneNonReconnueException avec la ligne donn�e
	 * @param ligne La ligne qui n'a pas pu �tre reconnue
	 */
	public LigneNonReconnueException(String ligne) {
		super("LigneNonReconnueException : " + ligne);
	}
}