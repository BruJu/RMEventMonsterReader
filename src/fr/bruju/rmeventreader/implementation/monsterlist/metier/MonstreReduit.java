package fr.bruju.rmeventreader.implementation.monsterlist.metier;

import java.util.ArrayList;
import java.util.List;

public class MonstreReduit {
	public Monstre monstre;
	public List<Integer> presence;
	
	public MonstreReduit(List<Monstre> list) {
		this.monstre = list.get(0);
		
		presence = new ArrayList<>();
		
		for (Monstre monstre : list) {
			if (!sontIdentiques(this.monstre, monstre)) {
				throw new RuntimeException("This hash is bad and you should feel bad");
			}
		}
		
		list.forEach(monstre -> presence.add(monstre.getBattleId()));
	}
	

	public static boolean sontIdentiques(Monstre a, Monstre b) {
		for (Positions pos : Positions.values()) {
			if (a.get(pos) != b.get(pos)) {
				return false;
			}
			
		}
		
		return a.name.equals(b.name)
				&& a.nomDrop.equals(b.nomDrop);
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
