package fr.bruju.rmeventreader.implementation.magasin.livre;

import fr.bruju.rmdechiffreur.ExecuteurInstructions;
import fr.bruju.rmdechiffreur.controlleur.ExtChangeVariable;
import fr.bruju.rmdechiffreur.controlleur.ExtCondition;
import fr.bruju.rmdechiffreur.modele.Comparateur;
import fr.bruju.rmdechiffreur.modele.ValeurFixe;
import fr.bruju.rmdechiffreur.modele.Variable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.Statistique;

import java.util.HashMap;
import java.util.Map;

public class LivresMenus implements ExecuteurInstructions, ExtCondition, ExtChangeVariable {
	private static final int VARIDOBJET = 427;
	private static final int VARCARAC = 1914;

	private Map<Integer, StatistiqueDeLivre> map = new HashMap<>();
	private int dernierIdObjetLu;

	public Map<Integer, StatistiqueDeLivre> getResultat() {
		return map;
	}

	@Override
	public boolean getBooleenParDefaut() {
		return true;
	}


	@Override
	public void affecterVariable(Variable valeurGauche, ValeurFixe valeurDroite) {
		if (valeurGauche.idVariable == VARCARAC) {

			StatistiqueDeLivre stat;

			if (valeurDroite.valeur == 15) {
				stat = StatistiqueDeLivre.Commandement;
			} else {
				stat = StatistiqueDeLivre.values()[valeurDroite.valeur - 1];
			}

			map.put(dernierIdObjetLu - 1000, stat);
		}
	}

	@Override
	public int variableFixe(int variable, Comparateur comparateur, ValeurFixe droite) {
		if (variable == VARIDOBJET) {
			dernierIdObjetLu = droite.valeur;
			map.put(droite.valeur - 1000, StatistiqueDeLivre.Erudition);
		}

		return 0;
	}
}
