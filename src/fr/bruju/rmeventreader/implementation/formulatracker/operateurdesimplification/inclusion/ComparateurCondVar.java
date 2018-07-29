package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion;

import java.util.Comparator;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;

// TODO : ranger plutot les conditions par "classe" (=, < > >= <=, !=)

public class ComparateurCondVar implements Comparator<Condition> {

	@Override
	public int compare(Condition arg0, Condition arg1) {
		// Les comparateurs ne sont pas sur des variables : on donne un ordre quelconque déterministe
		if (arg0 instanceof CVariable) {
			if (arg1 instanceof CVariable) {
				// Traitement à suivre
			} else {
				return 1;
			}
		} else {
			if (arg1 instanceof CVariable) {
				return -1;
			}  else {
				return Integer.compare(arg0.hashCode(), arg1.hashCode());
			}
		}
		
		CVariable c0 = (CVariable) arg0;
		CVariable c1 = (CVariable) arg1;
		
		if (!(c0.gauche.equals(c1.gauche))) {
			return Integer.compare(c0.gauche.hashCode(), c1.gauche.hashCode());
		}
		
		int cmpOperateur = comparerOperateurs(c0.operateur, c1.operateur);
		
		if (cmpOperateur != 0)
			return cmpOperateur;
		
		return Integer.compare(c0.droite.hashCode(), c1.droite.hashCode());
	}

	private int comparerOperateurs(Operator operateur, Operator operateur2) {
		int v1 = getPriorite(operateur);
		int v2 = getPriorite(operateur2);
		
		return Integer.compare(v1, v2);
	}

	private int getPriorite(Operator operateur) {
		switch (operateur) {
		case DIFFERENT:
			return 5;
		case IDENTIQUE:
			return 0;
		case INF:
			return 1;
		case INFEGAL:
			return 2;
		case SUP:
			return 3;
		case SUPEGAL:
			return 4;
		default:
			return 99;
		}
	}
	
}
