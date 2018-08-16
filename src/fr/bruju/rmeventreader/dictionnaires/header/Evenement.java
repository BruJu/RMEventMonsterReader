package fr.bruju.rmeventreader.dictionnaires.header;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

import fr.bruju.rmeventreader.dictionnaires.ExtractionXML;
import fr.bruju.rmeventreader.dictionnaires.UtilXML;

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

	public static Evenement instancier(Node eventNode) {
		int id = UtilXML.getId(eventNode);
		String nom = ExtractionXML.extraireFils(eventNode, "name");
		int x = Integer.parseInt(ExtractionXML.extraireFils(eventNode, "x"));
		int y = Integer.parseInt(ExtractionXML.extraireFils(eventNode, "y"));

		return new Evenement(id, nom, x, y);
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
}