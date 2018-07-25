package fr.bruju.rmeventreader;

import java.io.IOException;

import fr.bruju.rmeventreader.implementation.formulareader.FormulaMain;
import fr.bruju.rmeventreader.implementation.formulatracker.Exploitation;
import fr.bruju.rmeventreader.implementation.monsterlist.MonsterDBTest;
import fr.bruju.rmeventreader.rmdatabase.RMEventDatabaseMain;

public class Principal {
	public static void main(String[] args) throws IOException {
		int choix = 1;

		if (choix == 0)
			MonsterDBTest.main_(args, 3);

		if (choix == 1)
			FormulaMain.main_(args);
		
		if (choix == 2)
			RMEventDatabaseMain.main_();
		
		if (choix == 3)
			new Exploitation().exploiter();
		
	}
}
