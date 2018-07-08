package example;

import java.util.ArrayList;
import java.util.List;

import actionner.ActionMaker;
import actionner.Interpreter;

public class PrinterTest {

	public static void main(String[] args) {
		ActionMaker printer = new Printer();
		
		Interpreter interpreter = new Interpreter(printer);
		
		List<String> chaines = new ArrayList<>();
		
		chaines.add("<> Change Switch: [45] = ON");
		
		interpreter.inputLines(chaines);

	}

}
