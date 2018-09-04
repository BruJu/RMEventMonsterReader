package fr.bruju.rmeventreader;

import java.io.IOException;

import fr.bruju.rmeventreader.actionmakers.xml.AutoLibLcfXMLCache;
import fr.bruju.rmeventreader.dictionnaires.liblcfreader.CreateurDeRessources;
import fr.bruju.rmeventreader.dictionnaires.liblcfreader.MiseEnCache;
import fr.bruju.rmeventreader.implementation.formulatracker.FormulaTracker;
import fr.bruju.rmeventreader.implementation.monsterlist.ListeurDeMonstres;
import fr.bruju.rmeventreader.implementation.printer.PrintXML;
import fr.bruju.rmeventreader.implementation.printer.Printer;
import fr.bruju.rmeventreader.implementation.random.EventChecker;
import fr.bruju.rmeventreader.implementation.recomposeur.Recomposition;
import fr.bruju.rmeventreader.implementationexec.chercheurdevariables.ChercheurDeReferences;
import fr.bruju.rmeventreader.implementationexec.equipementchecker.Verificateur;
import fr.bruju.rmeventreader.implementationexec.magasin.ChercheurDeMagasins;
import fr.bruju.rmeventreader.implementationexec.random.ChercheurDImages;

public class Principal {
	/**
	 * Fonction principale
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("#### Début ####");

		int choix = 11;
		int choixMap = -1;
		
		if (args.length != 0) {
			choix = Integer.parseInt(args[0]);
		}
		
		if (args.length > 1) {
			choixMap = Integer.parseInt(args[1]);
		}
		
		Runnable[] options = {
				/* 0 */ new ListeurDeMonstres(3),
				/* 1 */ new FormulaTracker(),
				/* 2 */ new Recomposition(),
				/* 3 */ new PrintXML("ressources\\xml\\Map0001.xml", 1, 1),
				/* 4 */ new Createur(),
				/* 5 */ new Cache(choixMap),
				/* 6 */ new TestLectureCache(),
				/* 7 */ new Verificateur(),
				
				/* 8 */ new EventChecker(),
				/* 9 */ new ChercheurDImages(51),
				/* 10 */ new ChercheurDeReferences(),
				/* 11 */ new ChercheurDeMagasins(),
		};
		
		options[choix].run();
		
		System.out.println("#### Fin ####");
	}
	
	
	/**
	 * Crée des ressources (liste des objets, variables, switch, event communs et personnages) à partir des fichiers
	 * xml générés par lcf2xml
	 *
	 */
	public static class Createur implements Runnable {
		@Override
		public void run() {
			new CreateurDeRessources("ressources_gen\\").extraireBDD("ressources\\xml\\RPG_RT_DB.xml");
			new CreateurDeRessources("ressources_gen\\").extraireArbre("ressources\\xml\\RPG_RT_T.xml");
		}
	}
	
	/**
	 * Construit le cache xml.
	 * <p>
	 * On désigne comme cache le fait de transformer les fichiers xml générés par lcf2xml en une multitude de fichiers
	 * plus petits
	 *
	 */
	public static class Cache implements Runnable {
		private int choixMap;

		public Cache(int choixMap) {
			this.choixMap = choixMap;
		}

		@Override
		public void run() {
			if (choixMap == -1) {
				new MiseEnCache().construireCache("cache_xml\\", "ressources\\xml\\");
			} else { 
				new MiseEnCache(choixMap).construireCache("cache_xml\\", "ressources\\xml\\");
			}
		}
	}
	
	/**
	 * Lit un fichier créé par la mise en cache et affiche les instructions trouvée
	 *
	 */
	public static class TestLectureCache implements Runnable {
		@Override
		public void run() {
			//new AutoLibLcfXMLCache(new Printer(), -1, 14, -1).run();
			new AutoLibLcfXMLCache(new Printer(), 80, 162, -1).run();
		}
	}
	

}
