package fr.bruju.rmeventreader.implementationexec.magasin;

import fr.bruju.rmeventreader.actionmakers.Encyclopedie;

public class Objet implements Comparable<Objet> {
	public final int id;
	public final String nom;
	
	public Objet(int id, String nom) {
		this.id = id;
		this.nom = nom;
	}
	
	public Objet(Integer idObjet) {
		this(idObjet, new Encyclopedie().getSansSymbole("OBJET", idObjet-1));
	}

	public String getString() {
		return id + " " + nom;
	}

	@Override
	public int compareTo(Objet arg0) {
		return Integer.compare(this.id, arg0.id);
	}
}
