package fr.bruju.rmeventreader.implementation.monsterlist.metier;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;

/**
 * Représentation d'un monstre
 * @author Bruju
 *
 */
public class Monstre {
	/* =========
	 * Attributs 
	 * ========= */

	/**
	 * Combat associé
	 */
	private Combat combat;
	
	/**
	 * Statistiques du monstre
	 */
	private int[] stats;
	
	
	// Accessibles dans le package pour la classe Remplacement
	
	/**
	 * Nom du monstre
	 */
	String name = "UNKNOWN_NAME";
	
	/**
	 * Nom du drop
	 */
	String nomDrop = "";

	/**
	 * Immunité à fossile
	 */
	private boolean immuniteAFossile;
	
	public Monstre(Combat combat) {
		stats = new int[Positions.TAILLE];
		this.combat = combat;
	}
	
	public String getString() {
		String s = name;
		
		for (int i = 0 ; i != Positions.TAILLE ; i++) {
			s = s + ", " + stats[i];
		}
		
		s = s + ", " + (this.immuniteAFossile ? "Immunité" : "•");
		
		s = s + ", " + nomDrop;
		
		return s;
	}
	
	public int getId() {
		return this.stats[Positions.POS_ID.ordinal()];
	}

	public int getBattleId() {
		return combat.getId();
	}
	
	public String getBetterDisplay(int i) {
		String s = "=== " + i + " : " + name;
		
		for (Positions position : Positions.values()) {			
			s = s + "\n" + position + "[" + position.getVar(i) + "] = " + stats[position.ordinal()];
		}
		
		return s;
	}
	
	/* =============
	 * NON REFLEXION
	 * ============= */
	
	public void setId(int idMonstre) {
		this.stats[Positions.POS_ID.ordinal()] = idMonstre;
	}

	public int get(Positions posCapa) {
		return this.stats[posCapa.ordinal()];
	}
	
	
	public Long hasher() {
		long hash = 0;
		
		int x = 3;
		
		for (int i = 0 ; i != Positions.TAILLE ; i++) {
			hash = hash + stats[i] * x;
			
			x = x + 1000;
		}
		
		hash = hash + name.hashCode() * 50 + nomDrop.hashCode() + (this.immuniteAFossile ? 1 : 0);
		
		return hash;
	}
	
	
	
	/* =============
	 * AFFICHAGE CSV
	 * ============= */
	
	public String getCSV(boolean withBattleId) {
		StringBuilder sb = new StringBuilder();
		
		if (withBattleId) {
			sb.append(this.getBattleId());
			sb.append(";");
		}
		
		sb.append(this.getId());
		sb.append(";");
		sb.append(this.name);
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

	public static String getCSVHeader(boolean withBattleId) {
		String prefixe = "";
		
		if (withBattleId) {
			prefixe = "IDCOMBAT;";
		}
		
		return prefixe + "IDMONSTRE;NOM;DROP;" + Positions.getCSVHeader() + ";FOSSILE";
	}

	public String getNom() {
		return this.name;
	}

	public void apply(int posStat, Operator operator, int value) {
		stats[posStat] = operator.compute(stats[posStat], value);
	}

	public void setNom(String pictureName) {
		this.name = pictureName;
	}

	public void setDrop(String string) {
		this.nomDrop = string;
	}

	public void immuniserAFossile() {
		immuniteAFossile = true;
	}

	public boolean immunite() {
		return immuniteAFossile;
	}

	public String getDrop() {
		return nomDrop;
	}
	
}