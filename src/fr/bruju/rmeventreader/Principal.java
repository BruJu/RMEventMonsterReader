package fr.bruju.rmeventreader;

import java.io.IOException;

import fr.bruju.rmeventreader.dictionnaires.CreateurDeRessources;
import fr.bruju.rmeventreader.implementation.formulatracker.Exploitation;
import fr.bruju.rmeventreader.implementation.monsterlist.MonsterDBTest;
import fr.bruju.rmeventreader.implementation.printer.PrintXML;
import fr.bruju.rmeventreader.implementation.recomposeur.Recomposition;

public class Principal {
	public static void main(String[] args) throws IOException {
		System.out.println("Début");
		
		int choix = 7;

		if (choix == 0)
			MonsterDBTest.main_(args, 3);
		
		if (choix == 3)
			new Exploitation().exploiter();
		
		if (choix == 4)
			Recomposition.exploiter();

		
		if (choix == 6)
			PrintXML.printerMain(args);
		
		if (choix == 7) {
			new CreateurDeRessources("ressources_gen\\").extraireBDD("ressources\\xml\\RPG_RT_DB.xml");
			new CreateurDeRessources("ressources_gen\\").extraireArbre("ressources\\xml\\RPG_RT_T.xml");
		}
		
		System.out.println("Fin");
	}
}
