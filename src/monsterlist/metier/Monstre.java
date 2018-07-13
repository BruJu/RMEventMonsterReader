package monsterlist.metier;


public class Monstre {
	
	Combat combat;
	
	int[] stats;
	public String name;
	int drop;
	
	public Monstre(Combat combat) {
		stats = new int[Positions.TAILLE];
		this.combat = combat;
	}
	
	public String getString() {
		String s = "•";
		
		for (int i = 0 ; i != Positions.TAILLE ; i++) {
			s = s + ", " + stats[i];
		}
		
		return s;
	}
	
	public int getId() {
		return this.stats[Positions.POS_ID.ordinal()];
	}

	public int getBattleId() {
		return combat.getId();
	}
	
}