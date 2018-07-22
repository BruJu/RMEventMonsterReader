package fr.bruju.rmeventreader.utilitaire;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;

public class Utilitaire {
	public static String getSymbole(Operator operateur) {
		switch (operateur) {
		case AFFECTATION:
			return "←";
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

	public static int getPriorite(Operator operateur) {
		switch (operateur) {
		case MODULO:
			return 10;
		case TIMES:
		case DIVIDE:
			return 9;
		case MINUS:
		case PLUS:
			return 8;
		case AFFECTATION:
			return 7;
		case DIFFERENT:
		case IDENTIQUE:
			return 6;
		case INF:
			return 5;
		case INFEGAL:
			return 5;
		case SUP:
			return 5;
		case SUPEGAL:
			return 5;
		default:
			return Integer.MAX_VALUE;
		}
	}
}
