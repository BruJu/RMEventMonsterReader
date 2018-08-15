package fr.bruju.rmeventreader.implementation.printer;

import java.io.IOException;

import fr.bruju.rmeventreader.actionmakers.xml.InterpreterMapXML;

/**
 * Classe permettant de tester visuellement les effets de la classe Printer
 *
 */
public class PrintXML {
	/**
	 * Affiche dans la console les données lues pour des fichiers prédéfinis
	 * @param args
	 * @throws IOException
	 */
	public static void printerMain(String[] args) throws IOException {
		InterpreterMapXML interpreter = new InterpreterMapXML(new Printer());
		interpreter.inputFile("ressources/xml/Map0001.xml", 1, 1);
	}
}