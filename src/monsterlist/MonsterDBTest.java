package monsterlist;

import java.io.File;
import java.io.IOException;

import actionner.Interpreter;

public class MonsterDBTest {

	public static void main(String[] args) {
		MonsterDatabaseMaker printer = new MonsterDatabaseMaker();

		Interpreter interpreter = new Interpreter(printer);
		
		try {
			interpreter.inputFile(new File("ressources/CombatSuite.txt"));
			
			MonsterDatabase db = printer.get();
			
			System.out.println(db.getString());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
