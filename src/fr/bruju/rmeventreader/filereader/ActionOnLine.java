package fr.bruju.rmeventreader.filereader;

/**
 * Action réalisée à la lecture d'une ligne
 * 
 * @author Bruju
 *
 */
public interface ActionOnLine {
	/**
	 * Action réalisée lorsqu'une ligne est lue
	 * @param line La ligne lue
	 */
	public void read(String line);
}
