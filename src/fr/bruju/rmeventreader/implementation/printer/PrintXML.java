package fr.bruju.rmeventreader.implementation.printer;

import java.io.IOException;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;
import fr.bruju.rmeventreader.actionmakers.actionner.Interpreter;
import fr.bruju.rmeventreader.actionmakers.decrypter.InterpreteurCherry;
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
		ActionMaker printer = new Printer();

		Interpreter interpreter = new InterpreterMapXML(printer, 1, 1);
		
		interpreter.inputFile("ressources/Map0001.xml");
	}
}
