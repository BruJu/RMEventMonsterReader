package fr.bruju.rmeventreader.implementation.formulatracker.actionmaker;

import java.io.IOException;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.Explorateur;
import fr.bruju.rmeventreader.filereader.FileReaderByLine;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.Attaque;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.personnage.Personnages;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;

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
		
		attaquesParXML(contexte, attaques, "ressources/Attaques.txt");
	}


	private void attaquesParXML(Personnages contexte, Attaques attaques, String chemin) {
		FileReaderByLine.lectureFichierRessources(chemin, ligne -> {
			String[] donnees = FileReaderByLine.splitter(ligne, 3);
			
			Attaque attaque = creerAttaqueXML(contexte,
											donnees[1] + "-" + donnees[2],
											Integer.parseInt(donnees[0]));
			
			attaques.ajouterAttaque(attaque);
		});
	}


	private Attaque creerAttaqueXML(Personnages contexte, String nomAttaque, int numeroDEvent) {
		FormulaMaker formulaMaker = new FormulaMaker(contexte, false);
		Explorateur.lireEvenementCommun(formulaMaker, numeroDEvent);
		return new Attaque(nomAttaque, formulaMaker.getResultat());
	}
}
