package fr.bruju.rmeventreader.implementation.printer;

import java.io.File;
import java.io.IOException;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;
import fr.bruju.rmeventreader.actionmakers.actionner.Interpreter;
import fr.bruju.rmeventreader.actionmakers.decrypter.InterpreteurCherry;

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

		Interpreter interpreter = new InterpreteurCherry(printer);
		
		interpreter.inputFile(("ressources/Script.txt"));
		interpreter.inputFile(("ressources/CombatSuite.txt"));
		interpreter.inputFile(("ressources/CombatDrop.txt"));
		interpreter.inputFile(("ressources/Complement.txt"));
	}
}
