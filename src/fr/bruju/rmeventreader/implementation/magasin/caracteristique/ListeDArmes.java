package fr.bruju.rmeventreader.implementation.magasin.caracteristique;

import fr.bruju.rmdechiffreur.ExecuteurInstructions;
import fr.bruju.rmdechiffreur.controlleur.ExtChangeVariable;
import fr.bruju.rmdechiffreur.controlleur.ExtCondition;
import fr.bruju.rmdechiffreur.modele.Comparateur;
import fr.bruju.rmdechiffreur.modele.ValeurFixe;
import fr.bruju.rmdechiffreur.modele.Variable;
import fr.bruju.rmeventreader.ProjetS;

import java.util.HashMap;
import java.util.Map;

/**
 * Module lisant l'attaque donné par chaque id d'objet
 */
public class ListeDArmes implements ExecuteurInstructions, ExtChangeVariable, ExtCondition {
	/**
	 * Cherche dans tout le projet la liste des objets donnant de l'attaque
	 * @return Une Map associant id d'objet - attaque donnée par l'objet
	 */
	public static Map<Integer, Integer> lireBonusAttaque() {
		ListeDArmes listeur = new ListeDArmes();
		ProjetS.PROJET.lireEvenementCommun(listeur, 375);
		return listeur.getResultat();
	}

	/** Variable contenant l'id de l'objet */
	private static int VAR_OBJET = 828;
	/** Variable contenant le bonus d'attaque */
	private static int VAR_ATTAQUE = 1477;

	/** Association objet - attaque donnée */
	private Map<Integer, Integer> bonusEnAttaque = new HashMap<>();

	/** Valeur de x de la dernière instruction si VAR_OBJET = x */
	private int dernierIfLu = 0;

	/** Donne la map résultat */
	private Map<Integer, Integer> getResultat() {
		return bonusEnAttaque;
	}

	@Override
	public boolean getBooleenParDefaut() {
		return false;
	}

	@Override
	public void affecterVariable(Variable valeurGauche, ValeurFixe valeurDroite) {
		if (valeurGauche.idVariable == VAR_ATTAQUE) {
			if (dernierIfLu != 0) {
				bonusEnAttaque.put(dernierIfLu, valeurDroite.valeur);
			}
		}
	}

	@Override
	public int variableFixe(int variable, Comparateur comparateur, ValeurFixe droite) {
		if (variable == VAR_OBJET) {
			dernierIfLu = droite.valeur;
		} else {
			dernierIfLu = 0;
		}

		return 0;
	}

	@Override
	public void Flot_siFin() {
		dernierIfLu = 0;
	}
}
