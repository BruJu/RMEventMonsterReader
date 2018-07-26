package fr.bruju.rmeventreader.implementation.formulatracker.actionmaker;

import java.io.IOException;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.Personnages;
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
		
		attaques.ajouterRepertoire("ressources/formulatracker/attaques/");

		attaques.getAttaques().stream().forEach(attaque -> {
			FormulaMaker formulaMaker = new FormulaMaker(contexte, false);
			new AutoActionMaker(formulaMaker, attaque.getChemin()).faire();
			attaque.attacherResultat(formulaMaker.getResultat());
		});
	}

}
