package fr.bruju.rmeventreader.implementation.formulatracker.actionmaker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.actionmakers.decrypter.AutoEventFactory;
import fr.bruju.rmeventreader.actionmakers.xml.AutoLibLcfXMLCache;
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
		
		
		//attaquesParEventFactory(contexte, attaques);
		attaquesParXML(contexte, attaques, "ressources/Attaques.txt");
	}


	private void attaquesParXML(Personnages contexte, Attaques attaques, String chemin) {
		List<String[]> att;
		try {
			att = FileReaderByLine.lireFichier(chemin, 3);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		att.stream()
			.map(d -> creerAttaqueXML(contexte, d[1] + "-" + d[2], Integer.parseInt(d[0])))
			.forEach(attaques::ajouterAttaque);
	}


	@SuppressWarnings("unused")
	private void attaquesParEventFactory(Personnages contexte, Attaques attaques) {
		List<String[]> att = ajouterRepertoire("ressources/formulatracker/attaques/");

		att.stream()
		   .map(donnees -> creerAttaque(contexte, donnees[0], donnees[1]))
		   .forEach(attaques::ajouterAttaque);
	}
	
	
	private Attaque creerAttaque(Personnages contexte, String nomAttaque, String chemin) {
		FormulaMaker formulaMaker = new FormulaMaker(contexte, false);
		new AutoEventFactory(formulaMaker, chemin).run();
		
		return new Attaque(nomAttaque, formulaMaker.getResultat());
	}

	private Attaque creerAttaqueXML(Personnages contexte, String nomAttaque, int numeroDEvent) {
		System.out.println(nomAttaque);
		
		FormulaMaker formulaMaker = new FormulaMaker(contexte, false);
		new AutoLibLcfXMLCache(formulaMaker, -1, numeroDEvent, -1).run();;
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
