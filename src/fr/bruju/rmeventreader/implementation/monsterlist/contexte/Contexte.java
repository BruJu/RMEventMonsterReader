package fr.bruju.rmeventreader.implementation.monsterlist.contexte;

import java.util.*;
import java.util.function.Function;

import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Serialiseur;
import fr.bruju.util.Pair;

/**
 * Classe permettant de retrouver la position des variables qui sont écrites dans un fichier ressource.
 * <p>
 * 
 * <pre>
 * //Format du fichier
 * NB_MONSTRES int
 * 
 * - Variables -
 * ID int...
 * Niveau int...
 * EXP int...
 * Capacité int...
 * AutresStatistiques int...
 * ...
 * 
 * - Interrupteurs -
 * Propriétés int...
 * ...
 * 
 * - Modules -
 * NomDeLaVariable int
 * ...
 * 
 * </pre>
 * 
 * @author Bruju
 *
 */
public class Contexte {
	private static final int DECALAGE_PROPRIETE = 5000;
	
	/* ========
	 * CONTEXTE
	 * ======== */

	/** Statistiques des monstres */
	private Map<Integer, Pair<Integer, String>> statistiquesSurMonstres;
	/** Liste des statistiques */
	private List<String> statistiques;
	/** Liste des propriétés */
	private List<String> proprietes;
	/** Association nom de statistiques - numéros de variables */
	private Map<String, int[]> variablesConcernees;
	/** Liste des fonctions d'affichage avec une association nom du critère - affichage à produire */
	private Map<String, Function<Monstre, String>> fonctionsDaffichage;


	public void injecter(Serialiseur serialiseur) {
		serialiseur.ajouterChampALire("IDCombat", monstre -> Integer.toString(monstre.combat.id));
		serialiseur.ajouterChampALire("ID", monstre -> Integer.toString(monstre.getId()));
		serialiseur.ajouterChampALire("Nom", monstre -> monstre.nom);
		serialiseur.ajouterChampALire("Drop", monstre -> monstre.nomDrop);

		fonctionsDaffichage.forEach(serialiseur::ajouterChampALire);
	}

	/**
	 * Construit un contexte
	 */
	public Contexte() {
		this.statistiquesSurMonstres = new HashMap<>();
		this.statistiques = new ArrayList<>();
		this.proprietes = new ArrayList<>();
		this.variablesConcernees = new HashMap<>();
		this.fonctionsDaffichage = new LinkedHashMap<>();

		ajouterID(new int[] { 549, 550, 551 });
		ajouterStatistiqueValuee(new int[] { 555, 556, 557 }, "Niveau");
		ajouterStatistiqueValuee(new int[] { 574, 575, 576 }, "EXP");
		ajouterStatistiqueValuee(new int[] { 577, 578, 579 }, "Capacité");
		ajouterStatistiqueValuee(new int[] { 594, 595, 596 }, "Argent");
		ajouterStatistiqueValuee(new int[] { 514, 516, 517 }, "HP");
		ajouterStatistiqueValuee(new int[] { 533, 534, 535 }, "Force");
		ajouterStatistiqueValuee(new int[] { 530, 531, 532 }, "Défense");
		ajouterStatistiqueValuee(new int[] { 613, 614, 615 }, "Magie");
		ajouterStatistiqueValuee(new int[] { 570, 571, 572 }, "Esprit");
		ajouterStatistiqueValuee(new int[] { 527, 528, 529 }, "Dextérité");
		ajouterStatistiqueValuee(new int[] { 536, 537, 538 }, "Esquive");
		ajouterPropriete(new int[] { 537, 538, 539 }, "Fossile", "Immunisé", " ");
		ajouterPropriete(new int[] { 3484, 3485, 3486 }, "Humain", "Humain", " ");
	}

	/**
	 * DetecteurDeColissionsDInterrupteurs partir du numéro de la variable modifiée, donne un couple <numéro du monstre, statistique>
	 */
	public Pair<Integer, String> getStatistique(int position) {
		return statistiquesSurMonstres.get(position);
	}

	/**
	 * Donne la liste des statistiques
	 */
	public List<String> getStatistiques() {
		return statistiques;
	}

	/**
	 * DetecteurDeColissionsDInterrupteurs partir du numéro de l'interrupteur modifié, donne un couple <numéro du monstre, statistique>
	 */
	public Pair<Integer, String> getPropriete(int position) {
		return statistiquesSurMonstres.get(position + DECALAGE_PROPRIETE);
}

	/**
	 * Donne la liste des statistiques
	 */
	public List<String> getProprietes() {
		return proprietes;
	}

	/**
	 * Donne la liste des variables concernant une statistique
	 */
	public int[] getListeVariables(String nomStatistique) {
		return variablesConcernees.get(nomStatistique);
	}

	/**
	 * Ajoute l'ID du monstre à la liste des positions de variables connues
	 * @param variables Un tableau contenant les variables donnant l'ID de chaque monstre
	 */
	private void ajouterID(int[] variables) {
		this.statistiques.add("ID");

		for (int i = 0; i != variables.length; i++) {
			int idVariable = variables[i];
			statistiquesSurMonstres.put(idVariable, new Pair<>(i, "ID"));
		}

		variablesConcernees.put("ID", variables);
	}

	/**
	 * Ajoute une statistique représentée par une variable
	 * @param variables Un tableau contenant la position des variables
	 * @param nom Le nom de la statistique
	 */
	private void ajouterStatistiqueValuee(int[] variables, String nom) {
		this.statistiques.add(nom);

		for (int i = 0; i != variables.length; i++) {
			statistiquesSurMonstres.put(variables[i], new Pair<>(i, nom));
		}

		variablesConcernees.put(nom, variables);

		fonctionsDaffichage.put(nom, monstre -> Integer.toString(monstre.accessInt(nom)));
	}

	/**
	 * Ajoute une propriété (statistique représentée par un interrupteur)
	 * @param variables La liste des interrupteurs représentant cette propriété
	 * @param nom Le nom de la propriété
	 * @param siVrai Affichage à produire si l'interrupteur est activé
	 * @param siFaux Affichage à produire si l'interrupteur est désactivé
	 */
	private void ajouterPropriete(int[] variables, String nom, String siVrai, String siFaux) {
		this.proprietes.add(nom);

		for (int i = 0; i != variables.length; i++) {
			variables[i] += DECALAGE_PROPRIETE;
			statistiquesSurMonstres.put(variables[i], new Pair<>(i, nom));
		}

		variablesConcernees.put(nom, variables);

		fonctionsDaffichage.put(nom, monstre -> (monstre.accessBool(nom) ? siVrai : siFaux));
	}
}
