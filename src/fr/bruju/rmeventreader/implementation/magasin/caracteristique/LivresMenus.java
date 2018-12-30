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

public class LivresMenus implements ExecuteurInstructions, ExtCondition, ExtChangeVariable {
	public static Map<Integer, Livre.StatistiqueDeLivre> lireLesStatistiques() {
		LivresMenus livresMenus = new LivresMenus();
		PROJET.lireEvenementCommun(livresMenus, 351);
		return livresMenus.getResultat();
	}

	private static final int VARIDOBJET = 427;
	private static final int VARCARAC = 1914;

	private Map<Integer, Livre.StatistiqueDeLivre> map = new HashMap<>();
	private int dernierIdObjetLu;

	public Map<Integer, Livre.StatistiqueDeLivre> getResultat() {
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
