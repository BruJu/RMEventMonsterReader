package fr.bruju.rmeventreader.implementation.detectiondeformules;

import fr.bruju.rmeventreader.utilitaire.Utilitaire;
import fr.bruju.util.Pair;
import fr.bruju.util.table.Table;

import java.util.List;
import java.util.Stack;
import java.util.function.Function;

/**
 * Une classe qui transforme les Tables BJUtils en Table html
 */
public class TableVersTable {
	private StringBuilder sb;
	private final String chemin;

	public TableVersTable(String chemin) {
		this.chemin = chemin;
	}

	public void mettreLeHeader() {
		balise("html");
		balise("style");
		sb.append("table, td, tr, th { border: black solid 1px; border-collapse: collapse; }");
		fermer();
		balise("body");
	}

	public void mettreLeFooter() {
		fermer();
		fermer();
	}

	private Stack<String> pile = new Stack<>();

	public void balise(String nom) {
		sb.append("<" + nom + ">");
		pile.push(nom);
	}

	public void fermer() {
		sb.append("</" + pile.pop() + ">");
	}

	public void ecrire(String chaine) {
		sb.append(escapeHTML(chaine));
	}




	public void versHTML(Table table, List<Pair<String, Function<Object, String>>> colonnes) {
		sb = new StringBuilder();

		mettreLeHeader();

		balise("table");
		balise("tr");

		for (Pair<String, Function<Object, String>> colonne : colonnes) {
			balise("th");
			ecrire(colonne.getLeft());
			fermer();
		}

		fermer();

		table.forEach(enregistrement -> {
			balise("tr");

			for (Pair<String, Function<Object, String>> colonne : colonnes) {
				balise("td");
				ecrire(colonne.getRight().apply(enregistrement.get(colonne.getLeft())));
				fermer();
			}

			fermer();
		});

		fermer();

		mettreLeFooter();

		Utilitaire.Fichier_Ecrire(chemin, sb.toString());
	}

	// source : https://www.rgagnon.com/javadetails/java-0306.html
	public static final String escapeHTML(String s){
		StringBuffer sb = new StringBuffer();
		int n = s.length();
		for (int i = 0; i < n; i++) {
			char c = s.charAt(i);
			switch (c) {
				case '\n': sb.append("<br>"); break;
				case '<': sb.append("&lt;"); break;
				case '>': sb.append("&gt;"); break;
				case '&': sb.append("&amp;"); break;
				case '"': sb.append("&quot;"); break;
				case 'à': sb.append("&agrave;");break;
				case 'À': sb.append("&Agrave;");break;
				case 'â': sb.append("&acirc;");break;
				case 'Â': sb.append("&Acirc;");break;
				case 'ä': sb.append("&auml;");break;
				case 'Ä': sb.append("&Auml;");break;
				case 'å': sb.append("&aring;");break;
				case 'Å': sb.append("&Aring;");break;
				case 'æ': sb.append("&aelig;");break;
				case 'Æ': sb.append("&AElig;");break;
				case 'ç': sb.append("&ccedil;");break;
				case 'Ç': sb.append("&Ccedil;");break;
				case 'é': sb.append("&eacute;");break;
				case 'É': sb.append("&Eacute;");break;
				case 'è': sb.append("&egrave;");break;
				case 'È': sb.append("&Egrave;");break;
				case 'ê': sb.append("&ecirc;");break;
				case 'Ê': sb.append("&Ecirc;");break;
				case 'ë': sb.append("&euml;");break;
				case 'Ë': sb.append("&Euml;");break;
				case 'ï': sb.append("&iuml;");break;
				case 'Ï': sb.append("&Iuml;");break;
				case 'ô': sb.append("&ocirc;");break;
				case 'Ô': sb.append("&Ocirc;");break;
				case 'ö': sb.append("&ouml;");break;
				case 'Ö': sb.append("&Ouml;");break;
				case 'ø': sb.append("&oslash;");break;
				case 'Ø': sb.append("&Oslash;");break;
				case 'ß': sb.append("&szlig;");break;
				case 'ù': sb.append("&ugrave;");break;
				case 'Ù': sb.append("&Ugrave;");break;
				case 'û': sb.append("&ucirc;");break;
				case 'Û': sb.append("&Ucirc;");break;
				case 'ü': sb.append("&uuml;");break;
				case 'Ü': sb.append("&Uuml;");break;
				case '®': sb.append("&reg;");break;
				case '©': sb.append("&copy;");break;
				case '€': sb.append("&euro;"); break;
				// be carefull with this one (non-breaking whitee space)
				case ' ': sb.append("&nbsp;");break;

				default:  sb.append(c); break;
			}
		}
		return sb.toString();
	}
}
