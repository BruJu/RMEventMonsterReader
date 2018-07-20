package fr.bruju.rmeventreader.implementation.monsterlist.metier.mapreduce;

import java.util.Set;
import java.util.TreeSet;

import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;

public class MonstreValeur {

	/**
	 * Liste des combats présents
	 */
	private Set<Integer> combatsPresent = new TreeSet<>();

	/* ==========
	 * MAP REDUCE
	 * ========== */
	
	public MonstreValeur(Monstre emission) {
		combatsPresent.add(emission.getBattleId());
	}
	
	public MonstreValeur(MonstreValeur v1, MonstreValeur v2) {
		combatsPresent.addAll(v1.combatsPresent);
		combatsPresent.addAll(v2.combatsPresent);
	}

	/* =========
	 * AFFICHAGE
	 * ========= */
	
	
	public static String getCSVHeader() {
		return "Combats";
	}
	
	public String getCSV() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("[");
		
		boolean premier = true;
		
		for (Integer id : combatsPresent) {
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
