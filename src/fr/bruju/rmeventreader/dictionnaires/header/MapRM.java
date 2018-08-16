package fr.bruju.rmeventreader.dictionnaires.header;

import java.util.ArrayList;
import java.util.List;

public class MapRM implements ElementComposite<Evenement> {
	public final int id;
	public final String nom;
	public final List<String> arborescence;
	public final List<Evenement> evenements;
	
	public MapRM(int id, MapArbre[] arbre) {
		this.id = id;
		this.nom = arbre[id].nom;
		this.arborescence = new ArrayList<>();
		this.evenements = new ArrayList<>();
		
		remplirArbre(arborescence, id, arbre);
	}

	private static void remplirArbre(List<String> arborescence, int id, MapArbre[] arbre) {
		if (id != 0) {
			MapArbre paire = arbre[id];
			remplirArbre(arborescence, paire.idPere, arbre);
		}
		
		arborescence.add(arbre[id].nom);
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