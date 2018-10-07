package fr.bruju.rmeventreader;

import java.io.IOException;

import fr.bruju.rmeventreader.implementation.chercheurdevariables.ChercheurDeReferences;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.FormulaTracker;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.Recomposition;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.Simplifieur;
import fr.bruju.rmeventreader.implementation.equipementchecker.Verificateur;
import fr.bruju.rmeventreader.implementation.magasin.ChercheurDeMagasins;
import fr.bruju.rmeventreader.implementation.monsterlist.ListeurDeMonstres;
import fr.bruju.rmeventreader.implementation.random.AppelsDEvenements;
import fr.bruju.rmeventreader.implementation.random.ChercheurDImages;

import static fr.bruju.rmeventreader.ProjetS.PROJET;

/** Classe principale */
public class Principal {
	/** Fonction principale */
	public static void main(String[] args) throws IOException {
		System.out.println("#### Début ####");

		int choix = 9;
		
		if (args.length != 0) {
			choix = Integer.parseInt(args[0]);
		}
		
		Runnable[] options = {
				/* 00 */ new ListeurDeMonstres(7),
				/* 01 */ new FormulaTracker(),
				/* 02 */ new Recomposition(),
				/* 03 */ () -> PROJET.ecrireRessource("ressources_gen\\"),
				/* 04 */ new Verificateur(),
				/* 05 */ new AppelsDEvenements(),
				/* 06 */ new ChercheurDImages(51),
				/* 07 */ new ChercheurDeReferences(),
				/* 08 */ new ChercheurDeMagasins(),
				/* 09 */ new Simplifieur()
		};
		
		options[choix].run();
		
		System.out.println("#### Fin ####");
	}
}
