package fr.bruju.rmeventreader.implementation.magasin.caracteristique;

import fr.bruju.rmdechiffreur.ExecuteurInstructions;
import fr.bruju.rmdechiffreur.controlleur.ExtChangeVariable;
import fr.bruju.rmdechiffreur.controlleur.ExtCondition;
import fr.bruju.rmdechiffreur.modele.Comparateur;
import fr.bruju.rmdechiffreur.modele.ValeurFixe;
import fr.bruju.rmdechiffreur.modele.Variable;
import fr.bruju.rmeventreader.implementation.magasin.objet.Livre;

import java.util.HashMap;
import java.util.Map;

import static fr.bruju.rmeventreader.ProjetS.PROJET;

/**
 * Détecte la liste des livres et la statistique qu'ils donnent
 */
public class LivresMenus implements ExecuteurInstructions, ExtCondition, ExtChangeVariable {
	/**
	 * Lit l'évènement commun dédié aux livres et renvoie la liste des livres avec la statistique augmentée par le livre
	 * @return Une association id objet - statistique augmentée si c'est un livre.
	 */
	public static Map<Integer, Livre.StatistiqueDeLivre> lireLesStatistiques() {
		LivresMenus livresMenus = new LivresMenus();
		PROJET.lireEvenementCommun(livresMenus, 351);
		return livresMenus.getResultat();
	}

	/*
	 * On recherche des instructions du type
	 * si V[VARIDOBJET] == IDLIVRE
	 *   V[VARCARAC] = IDSTAT
	 * fin si
	 *
	 * Si on trouve ce genre d'instructions, on souhaite map.put(IDLIVRE, IDSTAT);
	 */

	/** Variable contenant l'id de objet */
	private static final int VARIDOBJET = 427;
	/** Variable contenant l'id de la caractéristique */
	private static final int VARCARAC = 1914;

	/** Map résultat */
	private Map<Integer, Livre.StatistiqueDeLivre> map = new HashMap<>();

	/** ID de l'objet sur lequel la dernière condition a porté */
	private int dernierIdObjetLu;

	/**
	 * Donne le résultat de l'exploration (la liste des associations id objet - caractéristique modifiée)
	 * @return Le résultat
	 */
	private Map<Integer, Livre.StatistiqueDeLivre> getResultat() {
		return map;
	}

	@Override
	public boolean getBooleenParDefaut() {
		return true;
	}

	@Override
	public void affecterVariable(Variable valeurGauche, ValeurFixe valeurDroite) {
		if (valeurGauche.idVariable == VARCARAC) {
			map.put(dernierIdObjetLu - 1000, Livre.StatistiqueDeLivre.get(valeurDroite.valeur));
		}
	}

	@Override
	public int variableFixe(int variable, Comparateur comparateur, ValeurFixe droite) {
		if (variable == VARIDOBJET) {
			dernierIdObjetLu = droite.valeur;
			map.put(droite.valeur - 1000, Livre.StatistiqueDeLivre.Erudition);
		}

		return 0;
	}
}
