package fr.bruju.rmeventreader.implementation.monsterlist.autotraitement;

import java.io.File;
import java.io.IOException;

import fr.bruju.rmeventreader.implementation.monsterlist.Correspondeur;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Remplacement;

/**
 * Action consistant à faire remplacer des valeurs par d'autres à travers un dictionnaire.
 * 
 * @author Bruju
 *
 */
public class AutoCorrespondeur implements ActionAutomatique {
	/**
	 * Base de données
	 */
	private MonsterDatabase database;
	
	/**
	 * Fonctions de remplacement
	 */
	private Remplacement remplaceur;
	
	/**
	 * Nom du fichier source
	 */
	private String filename;
	
	/**
	 * Construit une action dont le but sera de remplacer des données dans la base de données en fonction du
	 * remplaceur et avec les données fournies par le fichier filename
	 * @param database La base de données
	 * @param remplaceur Les fonctions de remplacement
	 * @param filename Le fichier ressources qui contient des lignes de la forme valeurARemplacer valeurRemplacée
	 */
	public AutoCorrespondeur(MonsterDatabase database, Remplacement remplaceur, String filename) {
		this.database = database;
		this.remplaceur = remplaceur;
		this.filename = filename;
	}
	
	@Override
	public void faire() {
		Correspondeur correspondeur = new Correspondeur();
		try {
			correspondeur.lireFichier(new File(filename));
			correspondeur.searchAndReplace(database.extractMonsters(), remplaceur.getSearch(), remplaceur.getReplace());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
