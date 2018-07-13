package monsterlist;

import java.io.File;
import java.io.IOException;

import actionner.ActionMaker;
import actionner.ConditionalActionMaker;
import actionner.Interpreter;
import monsterlist.actionmaker.drop.DropCompleter;
import monsterlist.metier.MonsterDatabase;
import monsterlist.metier.Monstre.RemplacementNom;
import monsterlist.metier.Monstre.RemplacementDrop;

public class MonsterDBTest {

	public static void main(String[] args) throws IOException {

		// Init combat
		
		MonsterDatabaseMaker dbMaker = new MonsterDatabaseMaker();

		ActionMaker conditionalActionMaker = new ConditionalActionMaker(dbMaker);
		
		Interpreter interpreter = new Interpreter(conditionalActionMaker);
		
		interpreter.inputFile(new File("ressources/InitCombat1.txt"));
		interpreter.inputFile(new File("ressources/InitCombat2.txt"));
		
		MonsterDatabase db = dbMaker.get();
		
		// IDNom des monstres
		
		conditionalActionMaker = new ConditionalActionMaker(new CompleterWithShowPicture(db));
		interpreter = new Interpreter(conditionalActionMaker);
		
		interpreter.inputFile(new File("ressources/NomDesMonstres.txt"));
		

		// Correspondance IDNom - Nom
		
		Correspondeur correspondeurNom = new Correspondeur();
		correspondeurNom.lireFichier(new File("ressources/Dico/Correspondance_Monstres.txt"));
		correspondeurNom.searchAndReplace(db.extractMonsters(), new RemplacementNom());
		
		conditionalActionMaker = new ConditionalActionMaker(new DropCompleter(db));
		interpreter = new Interpreter(conditionalActionMaker);
		
		interpreter.inputFile(new File("ressources/CombatDrop.txt"));
		
		Correspondeur correspondeurObjets = new Correspondeur();
		correspondeurObjets.lireFichier(new File("ressources/Dico/Objets.txt"));
		correspondeurObjets.searchAndReplace(db.extractMonsters(), new RemplacementDrop());
		
		
		
		
		System.out.println(db.getString());

	}
}
