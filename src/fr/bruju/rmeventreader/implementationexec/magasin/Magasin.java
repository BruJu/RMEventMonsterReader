package fr.bruju.rmeventreader.implementationexec.magasin;

import java.util.Set;
import java.util.TreeSet;

public class Magasin {
	private int idMagasin;
	private String cheminMap;
	private int niveauHoldup;
	private Set<Objet> objets;
	
	public Magasin(int idMagasin, String cheminMap) {
		this.idMagasin = idMagasin;
		this.cheminMap = cheminMap;
		niveauHoldup = 0;
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
	
}
