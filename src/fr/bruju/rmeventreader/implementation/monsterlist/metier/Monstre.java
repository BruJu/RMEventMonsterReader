package fr.bruju.rmeventreader.implementation.monsterlist.metier;

import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Set;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;

/**
 * Représentation d'un monstre
 * 
 * @author Bruju
 *
 */
public class Monstre {
	private static String STATS = "Statistiques";
	private static String PROPRIETES = "Proprietes";

	/* =========
	 * ATTRIBUTS 
	 * ========= */

	/** Données associées au monstre */
	@SuppressWarnings("rawtypes")
	public LinkedHashMap<String, Donnees> donnees = new LinkedHashMap<>();

	/** Combat associé */
	public final Combat combat;

	/** Nom du monstre */
	public String nom = "UNKNOWN_NAME";

	/** Nom du drop */
	public String nomDrop = "";

	/** Contexte */
	public final Contexte contexte;

	// public LinkedHashMap<String, Boolean> parties;

	// public LinkedHashMap<String, Integer> resistancesElementaires;

	/**
	 * Construit un monstre pour le combat donné
	 */
	public Monstre(Combat combat) {
		this.combat = combat;
		this.contexte = combat.contexte;
		remplirStats();
	}

	/**
	 * Rempli les propriétés et les statistiques avec des valeurs par défaut
	 */
	private void remplirStats() {
		donnees.put(STATS, new Donnees<Integer>(this, contexte.getStatistiques(), 0, v -> v.toString()));
		donnees.put(PROPRIETES,
				new Donnees<Boolean>(this, contexte.getProprietes(), false, v -> (v) ? "Immunisé" : "•"));
	}

	/* ==========
	 * ACCESSEURS 
	 * ========== */

	/**
	 * Accède à l'ensemble des données qui sont considérées de type Integer.
	 * <p>
	 * Aucune vérification n'est faite concernant le type de données renvoyées
	 * @param nomDonnees Le nom de l'ensemble de données
	 * @return L'ensemble des données
	 */
	@SuppressWarnings("unchecked")
	public Donnees<Integer> accessInt(String nomDonnees) {
		return donnees.get(nomDonnees);
	}

	/**
	 * Accède à l'ensemble des données qui sont considérées de type booléens.
	 * <p>
	 * Aucune vérification n'est faite concernant le type de données renvoyées
	 * @param nomDonnees Le nom de l'ensemble de données
	 * @return L'ensemble des données
	 */
	@SuppressWarnings("unchecked")
	public Donnees<Boolean> accessBool(String nomDonnees) {
		return donnees.get(nomDonnees);
	}

	
	/**
	 * Donne l'id du monstre
	 */
	public int getId() {
		return (int) donnees.get(STATS).get("ID");
	}

	/**
	 * Modifie l'id du monstre (utilisé pour la correction)
	 * 
	 * @param idMonstre L'id du monstre
	 */
	public void setId(int idMonstre) {
		accessInt(STATS).set("ID", idMonstre);
	}

	/**
	 * Donne l'id du combat où le monstre apparait
	 */
	public int getBattleId() {
		return combat.id;
	}

	/**
	 * Donne la valeur de la statistique demandée
	 */
	public int get(String nomStatistique) {
		return stats.get(nomStatistique);
	}

	/**
	 * Applique l'opération au monstre pour la statistique donnée
	 * 
	 * @param posStat La statistique
	 * @param operator L'opérateur
	 * @param value La valeur à appliquer
	 */
	public void apply(String nomStatistique, Operator operator, int valeurDroite) {
		stats.compute(nomStatistique, (cle, valeurGauche) -> operator.compute(valeurGauche, valeurDroite));
	}

	/* ==========
	 * PROPRIETES 
	 * ========== */

	/** Donne une proprieté au monstre */
	public void donnerPropriete(String nomPropriete) {
		this.proprietes.put(nomPropriete, true);
	}

	/** Renvoie si le monstre possède la propriété */
	public boolean aPropriete(String nomPropriete) {
		return proprietes.get(nomPropriete);
	}

	/* =========
	 * AFFICHAGE
	 * ========= */

	/**
	 * Donne un affichage du monstre
	 */
	public String getString() {
		StringBuilder sb = new StringBuilder();

		sb.append(nom);

		this.stats.forEach((nomStat, valeur) -> sb.append(",").append(valeur));

		this.proprietes.forEach((nomPropriete, valeur) -> sb.append(",").append(valeur ? "Immunité" : "•"));

		sb.append(",").append(nomDrop);

		return sb.toString();
	}

	/* ========================
	 * REDUCTION PAR SIMILAIRES
	 * ======================== */

	/** Renvoie un hash pour la partie clé du monstre */
	public int hasher() {
		return Objects.hash(nom, nomDrop);
	}

	/** Renvoie vrai si les parties clés des deux monstres */
	public static boolean sontSimilaires(Monstre a, Monstre b) {
		for (String pos : a.stats.keySet()) {
			if (a.get(pos) != b.get(pos)) {
				return false;
			}
		}

		for (String pos : a.proprietes.keySet()) {
			if (a.aPropriete(pos) != b.aPropriete(pos)) {
				return false;
			}
		}

		return a.nom.equals(b.nom) && a.nomDrop.equals(b.nomDrop);
	}

	/* =============
	 * AFFICHAGE CSV
	 * ============= */

	/**
	 * Donne une représentation en csv du monstre
	 * 
	 * @param withBattleId Si vrai inclus l'id du combat à l'affichage
	 * @return La représentation
	 */
	public String getCSV(boolean withBattleId) {
		StringBuilder sb = new StringBuilder();

		if (withBattleId) {
			sb.append(this.getBattleId());
			sb.append(";");
		}

		sb.append(this.getId());
		sb.append(";");
		sb.append(this.nom);
		sb.append(";");
		sb.append(this.nomDrop);

		stats.forEach((stat, valeur) -> {
			if (!stat.equals("ID")) {
				sb.append(";").append(valeur);
			}
		});

		this.proprietes.forEach((nomPropriete, valeur) -> sb.append(";").append(valeur ? "Immunité" : "•"));

		if (withBattleId) {
			sb.append(";").append(this.combat.fonds);
		}

		return sb.toString();
	}

	/**
	 * Renvoie le header du CSV de l'affichage d'un monstre
	 * 
	 * @param withBattleId Si vrai inclus l'id du combat à l'affichage
	 * @return La représentation
	 */
	public String getCSVHeader(boolean withBattleId) {
		String prefixe = "";

		if (withBattleId) {
			prefixe = "IDCOMBAT;";
		}

		prefixe = prefixe + "IDMONSTRE;NOM;DROP;" + contexte.getCSVHeader();

		if (withBattleId) {
			prefixe += ";ZONE";
		}

		return prefixe;
	}

	/* =============================
	 * AFFICHAGE CSV DE LA REDUCTION
	 * ============================= */

	/**
	 * Donne le header d'un monstre pour les monstres réduits
	 */
	public String getCSVHeader() {
		return getCSVHeader(false) + ";" + "Combats";
	}

	/**
	 * Donne la représentation d'un monstre réduit
	 */
	public String getCSV() {
		return getCSV(false);
	}

}