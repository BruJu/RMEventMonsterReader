package fr.bruju.rmeventreader;

import java.io.IOException;

import fr.bruju.rmeventreader.implementation.formulareader.FormulaMain;
import fr.bruju.rmeventreader.implementation.monsterlist.MonsterDBTest;

public class Principal {
	public static void main(String[] args) throws IOException {
		MonsterDBTest.main_(args, 0);
		
		//FormulaMain.main_(args);
	}
}
