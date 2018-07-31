package fr.bruju.rmeventreader;

import java.io.IOException;

import fr.bruju.rmeventreader.implementation.formulatracker.Exploitation;
import fr.bruju.rmeventreader.implementation.monsterlist.MonsterDBTest;

public class Principal {
	public static void main(String[] args) throws IOException {
		int choix = 3;

		if (choix == 0)
			MonsterDBTest.main_(args, 3);
		
		if (choix == 3)
			new Exploitation().exploiter();
		
	}
}
