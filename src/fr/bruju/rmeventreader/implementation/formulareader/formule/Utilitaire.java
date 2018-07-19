package fr.bruju.rmeventreader.implementation.formulareader.formule;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;

public class Utilitaire {
	public static String getSymbole(Operator operateur) {
		switch (operateur) {
		case AFFECTATION:
			return "<-";
		case DIFFERENT:
			return "≠";
		case DIVIDE:
			return "/";
		case IDENTIQUE:
			return "=";
		case INF:
			return "<";
		case INFEGAL:
			return "≤";
		case MINUS:
			return "-";
		case MODULO:
			return "%";
		case PLUS:
			return "+";
		case SUP:
			return ">";
		case SUPEGAL:
			return "≥";
		case TIMES:
			return "×";
		default:
			return "??";
		}
	}
}
