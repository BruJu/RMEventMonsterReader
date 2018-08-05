package fr.bruju.rmeventreader;

import java.io.IOException;

import fr.bruju.rmeventreader.implementation.formulatracker.Exploitation;
import fr.bruju.rmeventreader.implementation.monsterlist.MonsterDBTest;
import fr.bruju.rmeventreader.implementation.recomposeur.Recomposition;

public class Principal {
	public static void main(String[] args) throws IOException {
		int choix = 4;

		if (choix == 0)
			MonsterDBTest.main_(args, 6);
		
		if (choix == 3)
			new Exploitation().exploiter();
		
		if (choix == 4)
			Recomposition.exploiter();
		
	}
}
