package monsterlist.metier;


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
	
}