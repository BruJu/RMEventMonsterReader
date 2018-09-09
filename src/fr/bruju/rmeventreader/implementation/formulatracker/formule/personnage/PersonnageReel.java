package fr.bruju.rmeventreader.implementation.formulatracker.formule.personnage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Représente un personnage réel
 * @author Bruju
 *
 */
public class PersonnageReel implements Personnage, Comparable<PersonnageReel> {
	/* ===============
	 * PERSONNAGE REEL
	 * =============== */
	
	/** Nom du personnage */
	private final String nom;
	/** Association nom de statistiques - statistiques du personnage */
	private Map<String, Statistique> statistiques;
	/** Association nom de propriété - propriété du personnage */
	private Map<String, Statistique> proprietes;
	
	/**
	 * Crée le personnage avec le nom donné
	 * @param nom Le nom du personnage
	 */
	public PersonnageReel(String nom) {
		this.nom = nom;
		statistiques = new HashMap<>();
		proprietes = new HashMap<>();
	}
	
	/**
	 * Ajoute une statistique au personnage
	 * @param nom Le nom de la statistique
	 * @param position Le numéro de sa variable
	 */
	public void addStatistique(String nom, int position) {
		statistiques.put(nom, new Statistique(this, nom, position));
	}

	/**
	 * Ajoute une propriété au personnage
	 * @param nom Le nom de la propriété
	 * @param position Le numéro de son interrupteur
	 */
	public void addPropriete(String nom, int position) {
		proprietes.put(nom, new Statistique(this, nom, position));
	}
	
	/* ==========
	 * PERSONNAGE
	 * ========== */
	
	@Override
	public String getNom() {
		return nom;
	}

	@Override
	public Map<String, Statistique> getStatistiques() {
		return statistiques;
	}

	@Override
	public Map<String, Statistique> getProprietes() {
		return proprietes;
	}

	@Override
	public Set<PersonnageReel> getPersonnagesReels() {
		Set<PersonnageReel> set = new TreeSet<>();
		set.add(this);
		return set;
	}

	/* ==========
	 * COMPARABLE
	 * ========== */
	
	@Override
	public int compareTo(PersonnageReel p2) {
		return getNom().compareTo(p2.getNom());
	}
}
