package monsterlist.autotraitement;

import java.io.File;
import java.io.IOException;

import monsterlist.Correspondeur;
import monsterlist.metier.MonsterDatabase;
import monsterlist.metier.Monstre.Remplacement;

public class AutoCorrespondeur implements ActionAutomatique {
	private MonsterDatabase database;
	private Remplacement remplaceur;
	private String filename;
	
	
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
			correspondeur.searchAndReplace(database.extractMonsters(), remplaceur);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
