package fr.bruju.rmeventreader;

import java.io.IOException;

import fr.bruju.rmeventreader.dictionnaires.CreateurDeRessources;
import fr.bruju.rmeventreader.dictionnaires.MiseEnCache;
import fr.bruju.rmeventreader.implementation.formulatracker.FormulaTracker;
import fr.bruju.rmeventreader.implementation.monsterlist.ListeurDeMonstres;
import fr.bruju.rmeventreader.implementation.printer.PrintXML;
import fr.bruju.rmeventreader.implementation.recomposeur.Recomposition;

public class Principal {
	public static void main(String[] args) throws IOException {
		System.out.println("#### Début ####");

		int choix = 5;
		
		if (args.length != 0) {
			choix = Integer.parseInt(args[0]);
		}
		
		Runnable[] options = {
				/* 0 */ new ListeurDeMonstres(3),
				/* 1 */ new FormulaTracker(),
				/* 2 */ new Recomposition(),
				/* 3 */ new PrintXML("ressources\\xml\\Map0001.xml", 1, 1),
				/* 4 */ new Createur(),
				/* 5 */ new Cache()
		};
		
		options[choix].run();
		
		System.out.println("#### Fin ####");
	}
	
	public static class Createur implements Runnable {
		@Override
		public void run() {
			new CreateurDeRessources("ressources_gen\\").extraireBDD("ressources\\xml\\RPG_RT_DB.xml");
			new CreateurDeRessources("ressources_gen\\").extraireArbre("ressources\\xml\\RPG_RT_T.xml");
		}
	}
	
	public static class Cache implements Runnable {

		@Override
		public void run() {
			new MiseEnCache().construireCache("cache_xml\\", "ressources\\xml\\");
			//new MiseEnCache().eventCommuns("cache_xml\\EC\\", "ressources\\xml\\RPG_RT_DB.xml");
			//new MiseEnCache().arbo("cache_xml\\", "ressources_gen\\bdd_maps.txt", "ressources\\xml\\Map");
		}
		
		
	}
}
