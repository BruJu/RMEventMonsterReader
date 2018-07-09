package example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import actionner.ActionMaker;
import actionner.Interpreter;

public class PrinterTest {

	public static void main(String[] args) {
		ActionMaker printer = new Printer();

		Interpreter interpreter = new Interpreter(printer);
		
		/*
		
		List<String> chaines = new ArrayList<>();
		
		chaines.add("<> Change Switch: [45] = ON");
		chaines.add("<> Fork Condition: If Switch [509] == OFF then ...");
		chaines.add("<> Fork Condition: If Switch [4] == ON then ...");
		chaines.add(" <> Fork Condition: If Hero #7 has item #844 equipped then ...");
		chaines.add(" <> Change Variable: [1818] += 3");
		chaines.add(" <>");
		chaines.add("<> Fork Condition: If Variable [2518] == 0 then ...");
		chaines.add("  <> Show Picture: #80, combfond-4, (160, 200), Mgn 100%, Tsp 100%/100%");
		chaines.add("  <> Show Picture: #109, fencombat2, (160, 200), Mgn 100%, Tsp 100%/100%");
		
		*/

		
		
		try {
			interpreter.inputFile(new File("ressources/Script.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
