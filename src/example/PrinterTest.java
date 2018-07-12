package example;

import java.io.File;
import java.io.IOException;

import actionner.ActionMaker;
import actionner.Interpreter;

public class PrinterTest {

	public static void main2(String[] args) {
		ActionMaker printer = new Printer();

		Interpreter interpreter = new Interpreter(printer);
		
		try {
			interpreter.inputFile(new File("ressources/Script.txt"));
			interpreter.inputFile(new File("ressources/CombatSuite.txt"));
			interpreter.inputFile(new File("ressources/CombatDrop.txt"));
			interpreter.inputFile(new File("ressources/Complement.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
