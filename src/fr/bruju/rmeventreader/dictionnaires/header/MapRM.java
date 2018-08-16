package fr.bruju.rmeventreader.dictionnaires.header;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.utilitaire.Pair;

public class MapRM implements ElementComposite<Evenement> {
	public final int id;
	public final String nom;
	public final List<String> arborescence;
	public final List<Evenement> evenements;
	
	public MapRM(int id, String nom, Pair<Integer, String>[] arbre) {
		this.id = id;
		this.nom = nom;
		this.arborescence = new ArrayList<>();
		this.evenements = new ArrayList<>();
		
		remplirArbre(arborescence, id, arbre);
	}

	private static void remplirArbre(List<String> arborescence, int id, Pair<Integer, String>[] arbre) {
		if (id != 0) {
			Pair<Integer, String> paire = arbre[id];
			remplirArbre(arborescence, paire.getLeft(), arbre);
		}
		
		arborescence.add(arbre[id].getRight());
	}
	
	public void append(StringBuilder sb) {
		sb.append("-- MAP --\n")
		  .append("ID ").append(id).append("\n")
		  .append("Nom ").append(nom).append("\n")
		  .append("CHEMIN");

		arborescence.forEach(map -> sb.append(">").append(map));
		sb.append("\n");
	}

	@Override
	public void ajouter(Evenement t) {
		this.evenements.add(t);
	}
	
	public void viderMemoire() {
		this.evenements.clear();
	}
}