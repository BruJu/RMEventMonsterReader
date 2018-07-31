package fr.bruju.rmeventreader.implementation.monsterlist.metier;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Objects;

/**
 * Représentation d'un monstre
 * 
 * @author Bruju
 *
 */
public class Monstre {
	public static String STATS = "Statistiques";
	public static String PROPRIETES = "Proprietes";

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
	 * 
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
	 * 
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

	/* =========
	 * AFFICHAGE
	 * ========= */

	/**
	 * Donne un affichage du monstre
	 */
	public String getString() {
		StringBuilder sb = new StringBuilder();

		sb.append(nom);

		sb.append(donnees.entrySet().stream().map(entrySet -> entrySet.getValue().getCSV())
				.collect(Collectors.joining(";")));

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
	@SuppressWarnings("rawtypes")
	public static boolean sontSimilaires(Monstre a, Monstre b) {

		for (Entry<String, Donnees> tuple : a.donnees.entrySet()) {
			Donnees adonnee = tuple.getValue();
			Donnees bdonnee = b.donnees.get(tuple.getKey());

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

		String data = donnees.entrySet().stream().map(entrySet -> entrySet.getValue().getCSV())
				.collect(Collectors.joining(";"));

		if (!withBattleId) {
			data = data.substring(data.indexOf(";"));
		}
		
		sb.append(data);

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

		prefixe = prefixe + "IDMONSTRE;NOM;DROP;";
		
		StringBuilder sb = new StringBuilder();
		
		this.donnees.forEach((nomEnsemble, donnees) -> sb.append(";").append(donnees.getHeader()));
		
		sb.delete(0, 4);
		
		prefixe += sb.toString();

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