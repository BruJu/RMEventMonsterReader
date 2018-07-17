package fr.bruju.rmeventreader.implementation.formulareader.actionmaker;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NewValeur;
import fr.bruju.rmeventreader.implementation.formulareader.formule.Valeur;
import fr.bruju.rmeventreader.implementation.formulareader.formule.ValeurNumerique;

public class Traduction {
	public static ValeurNumerique getValue(ValeurFixe value) {
		return NewValeur.Numerique(value.get());
	}

	public static ValeurNumerique getValue(ValeurAleatoire value) {
		return NewValeur.Numerique(value.getMin(), value.getMax());
	}

	public static Valeur getValue(Etat etat, Variable value) {
		int idVariable = value.get();
		
		return etat.getValeur(idVariable);
	}

	public static String getSymbole(Operator operator) {
		switch (operator) {
		case DIVIDE:
			return "/";
		case MINUS:
			return "-";
		case MODULO:
			return "%";
		case PLUS:
			return "+";
		case TIMES:
			return "*";
		default:
			break;
		}
		
		return "?";
	}
}
