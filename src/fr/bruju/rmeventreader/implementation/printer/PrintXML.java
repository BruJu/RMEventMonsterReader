package fr.bruju.rmeventreader.implementation.printer;

import java.io.IOException;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;
import fr.bruju.rmeventreader.actionmakers.actionner.Interpreter;
import fr.bruju.rmeventreader.actionmakers.xml.InterpreterMapXML;

/**
 * Classe permettant de tester visuellement les effets de la classe Printer
 *
 */
public class PrintXML {
	/*
	 * 
	 * 
	 * Types de monstre : Map 99, pages 1 à 18
	 * 
	 */
	
	
	/**
	 * Affiche dans la console les données lues pour des fichiers prédéfinis
	 * @param args
	 * @throws IOException
	 */
	public static void printerMain(String[] args) throws IOException {
		//ActionMaker printer = new Printer();

		initCombat2(new PrintComments());
		System.out.println("=====================================");
		System.out.println("=====================================");
		System.out.println("=============== MAP01 ===============");
		System.out.println("=====================================");
		map01(new Printer());
	}
	
	
	private static void map01(Printer printer) throws IOException {
		Interpreter interpreter = new InterpreterMapXML(printer, 1, 1);
		interpreter.inputFile("ressources/xml/Map0001.xml");
	}


	private static void initCombat2(ActionMaker printer) throws IOException {
		Interpreter interpreter = new InterpreterMapXML(printer, 102, 1);
		interpreter.inputFile("ressources/xml/Map0053.xml");
	}


	public static void typesDeMonstres(ActionMaker printer) throws IOException {
		for (int i = 1 ; i <= 18 ; i++ ) {
			
			System.out.println("=== PAGE " + i);
			Interpreter interpreter = new InterpreterMapXML(printer, 99, i);
			
			interpreter.inputFile("ressources/xml/Map0053.xml");
		}
	}
	
}

/*
new AutoActionMaker(new MonsterDatabaseMaker(baseDeDonnees)            , "ressources/InitCombat1.txt"),
new AutoActionMaker(new MonsterDatabaseMaker(baseDeDonnees)            , "ressources/InitCombat2.txt"),
new AutoActionMaker(new ExtracteurDeFond(baseDeDonnees)                , "ressources/InitCombat1.txt"),
new AutoActionMaker(new ExtracteurDeFond(baseDeDonnees)                , "ressources/InitCombat2.txt"),
new Correspondance<>(baseDeDonnees, Correspondance.Remplacement.fond() , "ressources/monsterlist/Zones.txt"),
new Correcteur(baseDeDonnees                                           , "ressources/Correction.txt"),
new AutoActionMaker(new NomDeMonstresViaShowPicture(baseDeDonnees)     , "ressources/NomDesMonstres.txt"),
new Correspondance<>(baseDeDonnees, Correspondance.Remplacement.nom()  , "ressources/Dico/Monstres.txt"),
new AutoActionMaker(new EnregistreurDeDrop(baseDeDonnees)              , "ressources/CombatDrop.txt"),
new Correspondance<>(baseDeDonnees, Correspondance.Remplacement.drop() , "ressources/Dico/Objets.txt"),
new SommeurDePointsDeCapacites(baseDeDonnees),
new AutoActionMaker(new FinDeCombat(baseDeDonnees)                     , "ressources/FinCombat.txt"),
*/
