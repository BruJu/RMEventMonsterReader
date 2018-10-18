package fr.bruju.rmeventreader;

import java.io.IOException;

import fr.bruju.rmdechiffreur.ExecuteurInstructions;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.ChercheurDeReferences;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.FormulaTracker;
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
	public static void main(String[] args) {
		System.out.println("#### Début ####");

		int choix = 8;
		
		if (args.length != 0) {
			choix = Integer.parseInt(args[0]);
		}
		
		Runnable[] options = {
				/* 00 */ new ListeurDeMonstres(3),
				/* 01 */ new FormulaTracker(),
				/* 02 */ () -> PROJET.ecrireRessource("ressources_gen\\"),
				/* 03 */ new Verificateur(),
				/* 04 */ new AppelsDEvenements(),
				/* 05 */ new ChercheurDImages(51),
				/* 06 */ new ChercheurDeReferences(),
				/* 07 */ new ChercheurDeMagasins(),
				/* 08 */ new Simplifieur()
		};
		
		options[choix].run();
		
		System.out.println("#### Fin ####");
	}
}
