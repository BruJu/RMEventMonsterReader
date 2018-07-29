package fr.bruju.rmeventreader.implementation.monsterlist.metier;

import java.util.Objects;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;

/**
 * Représentation d'un monstre
 * @author Bruju
 *
 */
public class Monstre {
	/* =========
	 * ATTRIBUTS 
	 * ========= */

	/** Combat associé */
	private Combat combat;
	
	/** Statistiques du monstre */
	private int[] stats;
	
	/** Nom du monstre */
	public String nom = "UNKNOWN_NAME";
	
	/** Nom du drop */
	public String nomDrop = "";

	/** Immunité à fossile */
	private boolean immuniteAFossile;
	
	/**
	 * Construit un monstre pour le combat donné
	 */
	public Monstre(Combat combat) {
		stats = new int[Positions.TAILLE];
		this.combat = combat;
	}
	
	/* ==========
	 * ACCESSEURS 
	 * ========== */
	
	/**
	 * Donne l'id du monstre
	 */
	public int getId() {
		return this.stats[Positions.POS_ID.ordinal()];
	}

	/**
	 * Modifie l'id du monstre
	 * @param idMonstre L'id du monstre
	 */
	public void setId(int idMonstre) {
		this.stats[Positions.POS_ID.ordinal()] = idMonstre;
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
	public int get(Positions posCapa) {
		return this.stats[posCapa.ordinal()];
	}
	
	/**
	 * Applique l'opération au monstre pour la statistique donnée
	 * @param posStat La statistique
	 * @param operator L'opérateur
	 * @param value La valeur à appliquer
	 */
	public void apply(int posStat, Operator operator, int value) {
		stats[posStat] = operator.compute(stats[posStat], value);
	}

	
	/* =======
	 * FOSSILE 
	 * ======= */

	/** Rend le monstre immunisé à fossile */
	public void immuniserAFossile() {
		immuniteAFossile = true;
	}

	/** Renvoie si le monstre est immunisé à fossile */
	public boolean immunite() {
		return immuniteAFossile;
	}
	

	/* =========
	 * AFFICHAGE
	 * ========= */
	
	/**
	 * Donne un affichage du monstre
	 */
	public String getString() {
		String s = nom;
		
		for (int i = 0 ; i != Positions.TAILLE ; i++) {
			s = s + ", " + stats[i];
		}
		
		s = s + ", " + (this.immuniteAFossile ? "Immunité" : "•");
		
		s = s + ", " + nomDrop;
		
		return s;
	}
	


	/* ========================
	 * REDUCTION PAR SIMILAIRES
	 * ======================== */
	
	/** Renvoie un hash pour la partie clé du monstre */
	public int hasher() {
		return Objects.hash(nom, nomDrop, immuniteAFossile);
	}
	

	/** Renvoie vrai si les parties clés des deux monstres */
	public static boolean sontSimilaires(Monstre a, Monstre b) {
		for (Positions pos : Positions.values()) {
			if (a.get(pos) != b.get(pos)) {
				return false;
			}
		}
		
		return a.nom.equals(b.nom) && a.nomDrop.equals(b.nomDrop) && a.immunite() == b.immunite();
	}
	
	
	/* =============
	 * AFFICHAGE CSV
	 * ============= */
	
	/**
	 * Donne une représentation en csv du monstre
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
		
		for (Positions position : Positions.values()) {
			if (position == Positions.POS_ID)
				continue;

			sb.append(";");
			sb.append(this.get(position));
		}
		
		sb.append(";");
		sb.append(this.immuniteAFossile ? "Immunisé" : "•");
		
		return sb.toString();
	}

	/**
	 * Renvoie le header du CSV de l'affichage d'un monstre
	 * @param withBattleId Si vrai inclus l'id du combat à l'affichage
	 * @return La représentation
	 */
	public static String getCSVHeader(boolean withBattleId) {
		String prefixe = "";
		
		if (withBattleId) {
			prefixe = "IDCOMBAT;";
		}
		
		return prefixe + "IDMONSTRE;NOM;DROP;" + Positions.getCSVHeader() + ";FOSSILE";
	}

	/* =============================
	 * AFFICHAGE CSV DE LA REDUCTION
	 * ============================= */
	
	/**
	 * Donne le header d'un monstre pour les monstres réduits
	 */
	public static String getCSVHeader() {
		return Monstre.getCSVHeader(false) + ";" + "Combats";
	}

	/**
	 * Donne la représentation d'un monstre réduit
	 */
	public String getCSV() {
		return getCSV(false);
	}
	
}