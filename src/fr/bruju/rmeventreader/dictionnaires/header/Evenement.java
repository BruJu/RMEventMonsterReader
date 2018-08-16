package fr.bruju.rmeventreader.dictionnaires.header;

import java.util.ArrayList;
import java.util.List;

public class Evenement implements ElementComposite<Page> {
	public final int id;
	public final String nom;
	public final int x;
	public final int y;
	public final List<Page> pages;

	public Evenement(int id, String nom, int x, int y) {
		this.id = id;
		this.nom = nom;
		this.x = x;
		this.y = y;
		this.pages = new ArrayList<>();
	}

	
	public void append(StringBuilder sb) {
		sb.append("-- EVENT --\n")
		  .append("ID ").append(id).append("\n")
		  .append("Nom ").append(nom).append("\n")
		  .append("Position ").append(x).append(",").append(y).append("\n")
		  .append("\n");
	}

	@Override
	public void ajouter(Page t) {
		this.pages.add(t);
	}
	
	/**
	 * Renvoie vrai si cet évènement n'est pas une page simple qui n'a aucune instruction et aucune condition
	 */
	public boolean estInteressant() {
		return !(pages.size() == 1 && pages.get(0).conditions.size() == 0 && pages.get(0).instructions.size() == 0);
	}
}