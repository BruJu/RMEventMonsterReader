package fr.bruju.rmeventreader.implementation.magasin.objet;

import java.util.Objects;

/**
 * Un objet simple
 */
public class Objet implements Comparable<Objet> {
	/** Le numéro */
	public final int id;
	/** Le nom */
	public final String nom;

	/**
	 * Crée un objet
	 * @param id Numéro de l'objet
	 * @param nom Le nom
	 */
	public Objet(int id, String nom) {
		this.id = id;
		this.nom = nom;
	}

	/**
	 * Donne une représentation de l'objet
	 * @return
	 */
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
