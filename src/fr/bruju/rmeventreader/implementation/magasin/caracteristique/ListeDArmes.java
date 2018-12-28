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

public class ListeDArmes implements ExecuteurInstructions, ExtChangeVariable, ExtCondition {
	public static Map<Integer, Integer> lireBonusAttaque() {
		ListeDArmes listeur = new ListeDArmes();
		ProjetS.PROJET.lireEvenementCommun(listeur, 375);
		return listeur.getResultat();
	}


	private Map<Integer, Integer> bonusEnAttaque = new HashMap<>();

	private static int VAR_OBJET = 828;
	private static int VAR_ATTAQUE = 1477;

	private int dernierIfLu = 0;

	public Map<Integer, Integer> getResultat() {
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
