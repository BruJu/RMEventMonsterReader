package fr.bruju.rmeventreader.implementation.formulatracker.actionmaker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.Attaque;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.personnage.Personnages;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;
import fr.bruju.rmeventreader.implementation.monsterlist.autotraitement.AutoActionMaker;

/**
 * Maillon permettant d'initialiser la base d'attaques en fonction d'un fichier ressources statistiques et des
 * attaques dans le dossier attaques.
 */
public class MaillonActionMaker implements Maillon {
	@Override
	public void traiter(Attaques attaques) {
		Personnages contexte = new Personnages();
		try {
			contexte.lirePersonnagesDansFichier("ressources/formulatracker/Statistiques.txt");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		List<String[]> att = ajouterRepertoire("ressources/formulatracker/attaques/");

		att.stream()
		   .map(donnees -> creerAttaque(contexte, donnees[0], donnees[1]))
		   .forEach(attaques.liste::add);
		
	}
	
	
	private Attaque creerAttaque(Personnages contexte, String nomAttaque, String chemin) {
		FormulaMaker formulaMaker = new FormulaMaker(contexte, false);
		new AutoActionMaker(formulaMaker, chemin).faire();
		
		return new Attaque(nomAttaque, formulaMaker.getResultat());
	}


	public List<String[]> ajouterRepertoire(String cheminRepertoire) {
		List<String[]> liste = new ArrayList<>();
		File repertoire = new File(cheminRepertoire);

		for (String fichier : repertoire.list()) {
			liste.add(getNewAttaque(cheminRepertoire, fichier));
		}
		return liste;
	}
	
	private String[] getNewAttaque(String chemin, String fichier) {
		String cheminComplet = chemin + fichier;
		String nomAttaque = fichier.substring(0, fichier.length() - 4);
		return new String[] {nomAttaque, cheminComplet};
	}
}
