package fr.bruju.rmeventreader.implementation.monsterlist.metier;


public class Monstre {


	
	Combat combat;
	
	int[] stats;
	public String name = "UNKNOWN_NAME";
	public String nomDrop = "";
	
	public Monstre(Combat combat) {
		stats = new int[Positions.TAILLE];
		this.combat = combat;
	}
	
	public String getString() {
		String s = name;
		
		for (int i = 0 ; i != Positions.TAILLE ; i++) {
			s = s + ", " + stats[i];
		}
		
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
	
	public static interface Remplacement {
		public String get(Monstre monstre);
		public void set(Monstre monstre, String value);
	}
	
	public static class RemplacementNom implements Remplacement {
		@Override
		public String get(Monstre monstre) {
			return monstre.name;
		}

		@Override
		public void set(Monstre monstre, String value) {
			monstre.name = value;
		}
	}
	
	public static class RemplacementDrop implements Remplacement {
		@Override
		public String get(Monstre monstre) {
			return monstre.nomDrop;
		}

		@Override
		public void set(Monstre monstre, String value) {
			monstre.nomDrop = value;
		}
	}

	public void setId(int idMonstre) {
		this.stats[Positions.POS_ID.ordinal()] = idMonstre;
	}

	public int getIdInBattleOf() {
		for (int i = 0 ; i != 3 ; i ++) {
			if (combat.monstres[i] == this)
				return i;
		}
		
		return -1;
	}

	public int get(Positions posCapa) {
		return this.stats[posCapa.ordinal()];
	}
	
	
	
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
		
		return sb.toString();
	}

	public static String getCSVHeader(boolean withBattleId) {
		String prefixe = "";
		
		if (withBattleId) {
			prefixe = "IDCOMBAT;";
		}
		
		return prefixe + "IDMONSTRE;NOM;DROP;" + Positions.getCSVHeader();
	}
	
}