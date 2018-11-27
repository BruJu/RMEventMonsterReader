package fr.bruju.rmeventreader.implementation.detectiondeformules.modele.personnage;

import java.util.Set;

/**
 * Un individu est un perwonnage qui ne représente qu'un seul personnage réel.
 */
public class Individu extends Personnage implements Comparable<Individu> {
	/** Nom du personnage */
	public final String nom;

	/**
	 * Crée un personnage
	 * @param nom Le nom du personnage
	 */
	public Individu(String nom) {
		this.nom = nom;
	}

	@Override
	public String getNom() {
		return nom;
	}

	@Override
	public void ajouterPersonnage(Set<Individu> set) {
		set.add(this);
	}

	@Override
	public int compareTo(Individu o) {
		return nom.compareTo(o.nom);
	}
}
