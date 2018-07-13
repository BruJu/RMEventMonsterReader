package monsterlist;

import java.io.File;
import java.io.IOException;

import actionner.ActionMaker;
import actionner.ConditionalActionMaker;
import actionner.Interpreter;

public class MonsterDBTest {

	public static void main(String[] args) {
		MonsterDatabaseMaker dbMaker = new MonsterDatabaseMaker();

		ActionMaker conditionalActionMaker = new ConditionalActionMaker(dbMaker);
		
		Interpreter interpreter = new Interpreter(conditionalActionMaker);
		
		try {
			interpreter.inputFile(new File("ressources/InitCombat1.txt"));
			interpreter.inputFile(new File("ressources/InitCombat2.txt"));
			
			MonsterDatabase db = dbMaker.get();
			
			System.out.println(db.getString());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
