package fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.actionmaker;

import java.io.IOException;

import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.Ressources;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.formule.attaques.Attaque;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.formule.attaques.IdentiteAttaque;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.formule.personnage.Personnages;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.operateurdesimplification.Maillon;
import fr.bruju.rmeventreader.utilitaire.LecteurDeFichiersLigneParLigne;

import static fr.bruju.rmeventreader.ProjetS.PROJET;

/**
 * Maillon permettant d'initialiser la base d'attaques en fonction d'un fichier ressources statistiques et des
 * attaques dans le dossier attaques.
 */
public class MaillonActionMaker implements Maillon {
	@Override
	public void traiter(Attaques attaques) {
		Personnages contexte = new Personnages();
		try {
			contexte.lirePersonnagesDansFichier(Ressources.STATISTIQUES);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		attaquesParXML(contexte, attaques, Ressources.ATTAQUES);
	}


	private void attaquesParXML(Personnages contexte, Attaques attaques, String chemin) {
		LecteurDeFichiersLigneParLigne.lectureFichierRessources(chemin, ligne -> {
			String[] donnees = LecteurDeFichiersLigneParLigne.diviser(ligne, 3);
			
			Attaque attaque = creerAttaqueXML(contexte,
											new IdentiteAttaque(donnees[2], donnees[1]),
											Integer.parseInt(donnees[0]));
			
			attaques.ajouterAttaque(attaque);
		});
	}


	private Attaque creerAttaqueXML(Personnages contexte, IdentiteAttaque nomAttaque, int numeroDEvent) {
		FormulaMaker formulaMaker = new FormulaMaker(contexte, false);
		PROJET.lireEvenementCommun(formulaMaker, numeroDEvent);
		return new Attaque(nomAttaque, formulaMaker.getResultat());
	}
}
