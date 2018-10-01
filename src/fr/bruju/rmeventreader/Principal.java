package fr.bruju.rmeventreader;

import java.io.IOException;

import fr.bruju.rmeventreader.dictionnaires.CreateurDeRessources;
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

		int choix = 8;
		
		if (args.length != 0) {
			choix = Integer.parseInt(args[0]);
		}
		
		
		Runnable[] options = {
				/* 00 */ new ListeurDeMonstres(3),
				/* 01 */ new FormulaTracker(),
				/* 02 */ new Recomposition(),
				/* 03 */ new Createur(),
				/* 04 */ new Verificateur(),
				/* 05 */ new AppelsDEvenements(),
				/* 06 */ new ChercheurDImages(51),
				/* 07 */ new ChercheurDeReferences(),
				/* 08 */ new ChercheurDeMagasins(),
		};
		
		options[choix].run();
		
		System.out.println("#### Fin ####");
	}
	
	
	/**
	 * Crée des ressources (liste des objets, variables, switch et personnages) à partir des fichiers
	 * xml générés par lcf2xml
	 *
	 */
	public static class Createur implements Runnable {
		@Override
		public void run() {
			new CreateurDeRessources("ressources_gen\\").extraireBDD();
		}
	}
	

}
