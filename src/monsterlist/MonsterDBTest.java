package monsterlist;

import java.io.File;
import java.io.IOException;

import actionner.ActionMaker;
import actionner.ConditionalActionMaker;
import actionner.Interpreter;
import monsterlist.metier.MonsterDatabase;

public class MonsterDBTest {

	public static void main(String[] args) {
		try {
			// Init combat
			
			MonsterDatabaseMaker dbMaker = new MonsterDatabaseMaker();

			ActionMaker conditionalActionMaker = new ConditionalActionMaker(dbMaker);
			
			Interpreter interpreter = new Interpreter(conditionalActionMaker);
			
			interpreter.inputFile(new File("ressources/InitCombat1.txt"));
			interpreter.inputFile(new File("ressources/InitCombat2.txt"));
			
			MonsterDatabase db = dbMaker.get();
			
			// Nom des monstres
			
			CompleterWithShowPicture dbPicNames = new CompleterWithShowPicture(db);
			
			conditionalActionMaker = new ConditionalActionMaker(dbPicNames);
			interpreter = new Interpreter(conditionalActionMaker);
			
			interpreter.inputFile(new File("ressources/NomDesMonstres.txt"));
			
			
			
			
			
			
			
			System.out.println(db.getString());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
