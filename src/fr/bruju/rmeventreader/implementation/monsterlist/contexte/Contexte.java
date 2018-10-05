package fr.bruju.rmeventreader.implementation.monsterlist.contexte;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.utilitaire.Pair;

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

	/**
	 * Construit un contexte
	 */
	public Contexte() {
		this.statistiquesSurMonstres = new HashMap<>();
		this.statistiques = new ArrayList<>();
		this.proprietes = new ArrayList<>();
		this.variablesConcernees = new HashMap<>();

		ajouterStatistique(new int[] { 549, 550, 551 }, "ID", false);
		ajouterStatistique(new int[] { 555, 556, 557 }, "Niveau", false);
		ajouterStatistique(new int[] { 574, 575, 576 }, "EXP", false);
		ajouterStatistique(new int[] { 577, 578, 579 }, "Capacité", false);
		ajouterStatistique(new int[] { 594, 595, 596 }, "Argent", false);
		ajouterStatistique(new int[] { 514, 516, 517 }, "HP", false);
		ajouterStatistique(new int[] { 533, 534, 535 }, "Force", false);
		ajouterStatistique(new int[] { 530, 531, 532 }, "Défense", false);
		ajouterStatistique(new int[] { 613, 614, 615 }, "Magie", false);
		ajouterStatistique(new int[] { 570, 571, 572 }, "Esprit", false);
		ajouterStatistique(new int[] { 527, 528, 529 }, "Dextérité", false);
		ajouterStatistique(new int[] { 536, 537, 538 }, "Esquive", false);
		ajouterStatistique(new int[] { 537, 538, 539 }, "Fossile", true);
		ajouterStatistique(new int[] { 3484, 3485, 3486 }, "Humain", true);
	}

	/**
	 * A partir du numéro de la variable modifiée, donne un couple <numéro du monstre, statistique>
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
	 * A partir du numéro de l'interrupteur modifié, donne un couple <numéro du monstre, statistique>
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

	private void ajouterStatistique(int[] variables, String nom, boolean estPropriete) {
		if (estPropriete) {
			this.proprietes.add(nom);
		} else {
			this.statistiques.add(nom);
		}

		for (int i = 0; i != variables.length; i++) {
			int idVariable = variables[i];
			if (estPropriete) {
				idVariable += DECALAGE_PROPRIETE;
			}

			statistiquesSurMonstres.put(idVariable, new Pair<>(i, nom));
		}

		variablesConcernees.put(nom, variables);
	}

}
