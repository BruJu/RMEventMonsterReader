package fr.bruju.rmeventreader.implementation.monsterlist.metier;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;

import java.util.Objects;

/**
 * Représentation d'un monstre
 * 
 * @author Bruju
 *
 */
public class Monstre {
	/* =========
	 * ATTRIBUTS 
	 * ========= */

	/** Données associées au monstre */
	public LinkedHashMap<String, Object> donnees = new LinkedHashMap<>();

	/** Combat associé */
	public final Combat combat;

	/** Nom du monstre */
	public String nom = "UNKNOWN_NAME";

	/** Nom du drop */
	public String nomDrop = "";

	/**
	 * Construit un monstre pour le combat donné
	 */
	public Monstre(Combat combat) {
		this.combat = combat;
		remplirStats();
	}

	/**
	 * Rempli les propriétés et les statistiques avec des valeurs par défaut
	 */
	private void remplirStats() {
		remplir(combat.contexte.getStatistiques(), 0);
		remplir(combat.contexte.getProprietes(), false);
	}

	public void remplir(Iterable<String> statistiques, Object valeurDeBase) {
		for (String statistique : statistiques) {
			donnees.put(statistique, valeurDeBase);
		}
	}

	public void assigner(String nomStatistique, Object nouvelObjet) {
		donnees.put(nomStatistique, nouvelObjet);
	}


	/* ==========
	 * ACCESSEURS 
	 * ========== */


	public void modifier(String nomDonnee, Function<Integer, Integer> modificateur) {
		donnees.put(nomDonnee, modificateur.apply((Integer) donnees.get(nomDonnee)));
	}

	/**
	 * Accède à l'ensemble des données qui sont considérées de type Integer.
	 * <p>
	 * Aucune vérification n'est faite concernant le type de données renvoyées
	 * 
	 * @param nomDonnees Le nom de l'ensemble de données
	 * @return L'ensemble des données
	 */
	public int accessInt(String nomDonnees) {
		return (Integer) donnees.get(nomDonnees);
	}

	/**
	 * Accède à l'ensemble des données qui sont considérées de type booléens.
	 * <p>
	 * Aucune vérification n'est faite concernant le type de données renvoyées
	 * 
	 * @param nomDonnees Le nom de l'ensemble de données
	 * @return L'ensemble des données
	 */
	public boolean accessBool(String nomDonnees) {
		return (Boolean) donnees.get(nomDonnees);
	}

	/**
	 * Donne l'id du monstre
	 */
	public int getId() {
		return accessInt("ID");
	}

	/**
	 * Modifie l'id du monstre (utilisé pour la correction)
	 * 
	 * @param idMonstre L'id du monstre
	 */
	public void setId(int idMonstre) {
		donnees.put("ID", idMonstre);
	}

	/**
	 * Donne l'id du combat où le monstre apparait
	 */
	public int getBattleId() {
		return combat.id;
	}

	/* ========================
	 * REDUCTION PAR SIMILAIRES
	 * ======================== */

	/** Renvoie un hash pour la partie clé du monstre */
	public int hasher() {
		return Objects.hash(nom, nomDrop);
	}

	/** Renvoie vrai si les parties clés des deux monstres */
	@SuppressWarnings("rawtypes")
	public static boolean sontSimilaires(Monstre a, Monstre b) {

		for (Entry<String, Object> tuple : a.donnees.entrySet()) {
			Object adonnee = tuple.getValue();
			Object bdonnee = b.donnees.get(tuple.getKey());

			if (!adonnee.equals(bdonnee)) {
				return false;
			}
		}

		if (!a.donnees.equals(b.donnees)) {
			return false;
		}

		return a.nom.equals(b.nom) && a.nomDrop.equals(b.nomDrop);
	}

	/* =============
	 * AFFICHAGE CSV
	 * ============= */

	public String serialiser(Serialiseur serialiseur) {
		return serialiseur.apply(this);
	}

}