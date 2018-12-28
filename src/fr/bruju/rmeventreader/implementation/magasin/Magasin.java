package fr.bruju.rmeventreader.implementation.magasin;

import fr.bruju.rmeventreader.implementation.magasin.objet.Objet;

import java.util.Set;
import java.util.TreeSet;

public class Magasin {
	private int idMagasin;
	private String cheminMap;
	private int niveauHoldup;
	private Set<Objet> objets;
	public final int variationPrix;
	
	public Magasin(int idMagasin, String cheminMap, int variationPrix) {
		this.idMagasin = idMagasin;
		this.cheminMap = cheminMap;
		niveauHoldup = 0;
		objets = new TreeSet<>();
		this.variationPrix = variationPrix;
	}

	/**
	 * Crée un nouveau magasin possédant le même id, emplacement et niveau de hold up que le magasin source. Ne reprend
	 * pas les objets vendus.
	 * @param source Le magasin source
	 */
	public Magasin(Magasin source) {
		this.idMagasin = source.idMagasin;
		this.cheminMap = source.cheminMap;
		niveauHoldup = source.niveauHoldup;
		this.variationPrix = source.variationPrix;
		objets = new TreeSet<>();
	}

	public int getId() {
		return idMagasin;
	}
	
	public void ajouterObjet(Objet objet) {
		objets.add(objet);
	}
	
	public void setNiveauHoldup(int niveau) {
		this.niveauHoldup = niveau;
	}
	
	public String getMagasinCompact() {
		return cheminMap + " @ " + niveauHoldup;
	}
	
	public String getMagasinComplet() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("> ")
		  .append(cheminMap)
		  .append("\n")
		  .append("ID : ")
		  .append(idMagasin)
		  .append("\n")
		  .append("Niveau :")
		  .append(niveauHoldup)
		  .append("\n")
		  .append("Objets :");
		
		objets.forEach(objet -> sb.append("\n").append(objet.getString()));
		
		sb.append("\n");
		
		return sb.toString();
	}

	public boolean possedeLObjetdID(int idObjet) {
		for (Objet objet : objets) {
			if (objet.id == idObjet) {
				return true;
			}
		}

		return false;
	}

	public String getLieu() {
		return cheminMap;
	}

	public boolean vendUnObjet() {
		return !objets.isEmpty();
	}

	public Iterable<Objet> objetsVendus() {
		return objets;
	}

	public static int transformerVariation(int variableDeBase) {
		switch (variableDeBase) {
			case 0:
			case 1:
				return 100;
			case 2:
				return 200;
			case 15:
				return 150;
			case -2:
				return 50;
			case -15:
				return 66;
			default:
				return variableDeBase;
		}
	}
}
