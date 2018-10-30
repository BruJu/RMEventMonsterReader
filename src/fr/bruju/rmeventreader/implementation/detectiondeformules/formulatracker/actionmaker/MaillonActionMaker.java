package fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.actionmaker;

import fr.bruju.rmeventreader.implementation.detectiondeformules.AttaqueALire;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.formule.attaques.Attaque;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.formule.personnage.Personnages;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.operateurdesimplification.Maillon;

import static fr.bruju.rmeventreader.ProjetS.PROJET;

/**
 * Maillon permettant d'initialiser la base d'attaques en fonction d'un fichier ressources statistiques et des
 * attaques dans le dossier attaques.
 */
public class MaillonActionMaker implements Maillon {
	@Override
	public void traiter(Attaques attaques) {
		Personnages contexte = new Personnages();
			
		for (AttaqueALire attaque : AttaqueALire.extraireAttaquesALire()) {
			attaques.ajouterAttaque(lireEvenementAttaque(contexte, attaque));
		}
	}

	private Attaque lireEvenementAttaque(Personnages contexte, AttaqueALire attaque) {
		FormulaMaker formulaMaker = new FormulaMaker(contexte, false);
		PROJET.lireEvenementCommun(formulaMaker, attaque.numeroEvenementCommun);
		return new Attaque(attaque, formulaMaker.getResultat());
	}
}
