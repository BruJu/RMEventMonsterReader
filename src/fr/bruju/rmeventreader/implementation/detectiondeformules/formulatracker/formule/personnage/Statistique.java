package fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.formule.personnage;

import fr.bruju.rmeventreader.implementation.detectiondeformules._personnages.Personne;

/**
 * Une statistique posséde par un personnage
 * @author Bruju
 *
 */
public class Statistique {
	/** Personnage possédant la statistique */
	public final Personne<StatPerso> possesseur;
	/** Nom de la statistique */
	public final String nom;
	/** Numéro de la variable si la statistique appartient à un personnage réel */
	public final int position;
	
	/**
	 * Crée une statistique appartenant au personnage donné et ayant le nom donné
	 * @param possesseur Le personnage ayant la statistique
	 * @param nom Le nom de la statistique
	 * @param position Le numéro de la variable dans laquelle est stockée la statistique
	 */
	public Statistique(Personne<StatPerso> possesseur, String nom, int position) {
		this.possesseur = possesseur;
		this.nom = nom;
		this.position = position;
	}
}
