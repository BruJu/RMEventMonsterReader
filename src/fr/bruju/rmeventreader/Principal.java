package fr.bruju.rmeventreader;

import java.io.IOException;

import fr.bruju.rmeventreader.dictionnaires.liblcfreader.CreateurDeRessources;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.ChercheurDeReferences;
import fr.bruju.rmeventreader.implementation.equipementchecker.Verificateur;
import fr.bruju.rmeventreader.implementation.formulatracker.FormulaTracker;
import fr.bruju.rmeventreader.implementation.magasin.ChercheurDeMagasins;
import fr.bruju.rmeventreader.implementation.monsterlist.ListeurDeMonstres;
import fr.bruju.rmeventreader.implementation.random.AppelsDEvenements;
import fr.bruju.rmeventreader.implementation.random.ChercheurDImages;
import fr.bruju.rmeventreader.implementation.recomposeur.Recomposition;

public class Principal {
	/**
	 * Fonction principale
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("#### Début ####");

		int choix = 10;
		
		if (args.length != 0) {
			choix = Integer.parseInt(args[0]);
		}
		
		
		Runnable[] options = {
				/* 0 */ new ListeurDeMonstres(3),
				/* 1 */ new FormulaTracker(),
				/* 2 */ new Recomposition(),
				/* 3 */ null,
				/* 4 */ new Createur(),
				/* 5 */ null,
				/* 6 */ null,
				/* 7 */ new Verificateur(),
				
				/* 8 */ new AppelsDEvenements(),
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
	

}
