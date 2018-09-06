package fr.bruju.rmeventreader.dictionnaires.modele;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.ConvertisseurLigneVersObjet;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.Traitement;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.base.Chemin;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.base.LigneAttendue;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.base.PaireIDString;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.base.TableauInt;

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

	public MapRM(int id, String nom, List<String> chemin) {
		this.id = id;
		this.nom = nom;
		this.arborescence = chemin;
		this.evenements = new ArrayList<>();
	}

	private static void remplirArbre(List<String> arborescence, int id, MapArbre[] arbre) {
		if (id != 0) {
			MapArbre paire = arbre[id];
			remplirArbre(arborescence, paire.idPere, arbre);
		}
		
		arborescence.add(arbre[id].nom);
	}
	
	@Override
	public void ajouter(Evenement t) {
		this.evenements.add(t);
	}
	
	public void viderMemoire() {
		this.evenements.clear();
	}
	

	
	
	public void append(StringBuilder sb) {
		sb.append("-- MAP --\n")
		  .append("ID ").append(id).append("\n")
		  .append("Nom ").append(nom).append("\n")
		  .append("CHEMIN");

		arborescence.forEach(map -> sb.append(">").append(map));
		sb.append("\n");
	}
	
	@SuppressWarnings("unchecked")
	public static ConvertisseurLigneVersObjet<MapRM, Builder> sousObjet() {
		return new ConvertisseurLigneVersObjet<>(new Builder(), new Traitement[] {
			new LigneAttendue<>("-- MAP --"),
			new TableauInt<Builder>("ID", (m, t) -> m.setID(t[0])),
			new PaireIDString<Builder>("Nom", (m, t) -> m.setNom(t)),
			new Chemin<Builder>("Chemin", (m, t) -> m.setChemin(t)),
		});
	}
	
	public static class Builder implements Monteur<MapRM> {
		private int id;
		private String nom;
		private List<String> chemin = new ArrayList<>();
		
		@Override
		public MapRM build() {
			return new MapRM(id, nom, chemin);
		}
		
		public Builder setID(int id) {
			this.id = id;
			return this;
		}
		
		public Builder setNom(String nom) {
			this.nom = nom;
			return this;
		}
		
		public Builder setChemin(String[] noms) {
			for (String nom : noms) {
				chemin.add(nom);
			}
			return this;
		}
	}

	public String getNom() {
		return this.arborescence.stream().collect(Collectors.joining("-"));
	}
}