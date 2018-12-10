package fr.bruju.rmeventreader.implementation.magasin;

import java.util.Objects;

import static fr.bruju.rmeventreader.ProjetS.PROJET;

public class Objet implements Comparable<Objet> {
	public final int id;
	public final String nom;
	
	public Objet(int id, String nom) {
		this.id = id;
		this.nom = nom;
	}
	
	public Objet(Integer idObjet) {
		this(idObjet, PROJET.extraireObjet(idObjet));
	}

	public String getString() {
		return id + " " + nom;
	}

	@Override
	public int compareTo(Objet arg0) {
		return Integer.compare(this.id, arg0.id);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Objet objet = (Objet) o;
		return id == objet.id &&
				Objects.equals(nom, objet.nom);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nom);
	}
}
