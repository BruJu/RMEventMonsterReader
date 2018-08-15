package fr.bruju.rmeventreader.implementation.printer;

import java.io.IOException;

import fr.bruju.rmeventreader.actionmakers.xml.InterpreterMapXML;

/**
 * Classe permettant de tester visuellement les effets de la classe Printer
 *
 */
public class PrintXML implements Runnable {
	private String fichier;
	private int idEvent;
	private int idPage;


	public PrintXML(String fichier, int idEvent, int idPage) {
		this.fichier = fichier;
		this.idEvent = idEvent;
		this.idPage = idPage;
	}
	
	/**
	 * Affiche dans la console les données lues pour des fichiers prédéfinis
	 * @param args
	 * @throws IOException
	 */
	public void run() {
		InterpreterMapXML interpreter = new InterpreterMapXML(new Printer());
		try {
			interpreter.inputFile(fichier, idEvent, idPage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}