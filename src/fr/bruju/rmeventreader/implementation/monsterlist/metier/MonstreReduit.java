package fr.bruju.rmeventreader.implementation.monsterlist.metier;

import java.util.ArrayList;
import java.util.List;

public class MonstreReduit {
	public Monstre monstre;
	public List<Integer> presence;
	
	public MonstreReduit(Monstre monstre) {
		this.monstre = monstre;
		presence = new ArrayList<>();
		presence.add(monstre.getBattleId());
	}
	
	public MonstreReduit clone() {
		return new MonstreReduit(monstre, presence);
	}
	
	private MonstreReduit(Monstre monstre, List<Integer> presence) {
		this.monstre = monstre;
		this.presence = new ArrayList<>();
		this.presence.addAll(presence);
	}

	public static boolean sontIdentiques(MonstreReduit ar, MonstreReduit br) {
		Monstre a = ar.monstre;
		Monstre b = br.monstre;
		
		for (Positions pos : Positions.values()) {
			if (a.get(pos) != b.get(pos)) {
				return false;
			}
			
		}
		
		return a.name.equals(b.name)
				&& a.nomDrop.equals(b.nomDrop);
	}

	public void recoit(MonstreReduit mr) {
		presence.addAll(mr.presence);
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
}
