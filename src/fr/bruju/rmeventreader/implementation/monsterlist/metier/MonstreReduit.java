package fr.bruju.rmeventreader.implementation.monsterlist.metier;

import java.util.ArrayList;
import java.util.List;

public class MonstreReduit {
	public Monstre monstre;
	public List<Integer> presence;
	
	

	public MonstreReduit(Monstre monstre) {
		this.monstre = monstre;
		presence = null;
	}


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
		MonstreReduit other = (MonstreReduit) obj;
		if (monstre == null) {
			if (other.monstre != null)
				return false;
		} else if (!sontIdentiques(monstre, other.monstre))
			return false;
		return true;
	}


	public static boolean sontIdentiques(Monstre a, Monstre b) {
		for (Positions pos : Positions.values()) {
			if (a.get(pos) != b.get(pos)) {
				return false;
			}
			
		}
		
		return a.name.equals(b.name)
				&& a.nomDrop.equals(b.nomDrop) && a.immunite() == b.immunite();
	}

	public static String getCSVHeader() {
		return Monstre.getCSVHeader(false) + ";Combats";
	}

	public String getCSV() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(monstre.getCSV(false));
		
		sb.append(";[");
		
		boolean premier = true;
		
		for (Integer id : presence) {
			if (premier) {
				premier = false;
			} else {
				sb.append(",");
			}
			
			sb.append(id);
		}
		
		
		sb.append("]");
		
		return sb.toString();
	}

	public void initierListePresence() {
		this.presence = new ArrayList<>();
	}

	public void addPresence(int battleId) {
		presence.add(battleId);
	}
}
