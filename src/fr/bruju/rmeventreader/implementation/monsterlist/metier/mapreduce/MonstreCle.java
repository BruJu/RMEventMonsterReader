package fr.bruju.rmeventreader.implementation.monsterlist.metier.mapreduce;

import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Positions;

public class MonstreCle {
	private Monstre monstre;
	
	public MonstreCle(Monstre monstre) {
		this.monstre = monstre;
	}
	
	/* =================
	 * HASHCODE / EQUALS
	 * ================= */
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((monstre == null) ? 0 : monstre.hasher()));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MonstreCle other = (MonstreCle) obj;
		if (monstre == null) {
			return other.monstre != null;
		} else {
			return clesSontIdentiques(monstre, other.monstre);
		}
	}

	public static boolean clesSontIdentiques(Monstre a, Monstre b) {
		for (Positions pos : Positions.values()) {
			if (a.get(pos) != b.get(pos)) {
				return false;
			}
			
		}
		
		return a.getNom().equals(b.getNom())
				&& a.getDrop().equals(b.getDrop()) && a.immunite() == b.immunite();
	}
	
	
	/* =========
	 * AFFICHAGE
	 * ========= */
	
	public static String getCSVHeader() {
		return Monstre.getCSVHeader(false);
	}

	public String getCSV() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(monstre.getCSV(false));
		
		return sb.toString();
	}

	public int getId() {
		return monstre.getId();
	}
}
