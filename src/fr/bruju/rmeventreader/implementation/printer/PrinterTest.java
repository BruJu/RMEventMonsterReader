package fr.bruju.rmeventreader.implementation.printer;

import java.io.File;
import java.io.IOException;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;
import fr.bruju.rmeventreader.actionmakers.actionner.Interpreter;

/**
 * Classe permettant de tester visuellement les effets de la classe Printer
 *
 */
public class PrinterTest {
	/**
	 * Affiche dans la console les données lues pour des fichiers prédéfinis
	 * @param args
	 * @throws IOException
	 */
	public static void printerMain(String[] args) throws IOException {
		ActionMaker printer = new Printer();

		Interpreter interpreter = new Interpreter(printer);
		
		interpreter.inputFile(new File("ressources/Script.txt"));
		interpreter.inputFile(new File("ressources/CombatSuite.txt"));
		interpreter.inputFile(new File("ressources/CombatDrop.txt"));
		interpreter.inputFile(new File("ressources/Complement.txt"));
	}
}
