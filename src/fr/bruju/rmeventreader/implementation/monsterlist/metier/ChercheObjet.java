package fr.bruju.rmeventreader.implementation.monsterlist.metier;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ChercheObjet {
	String resultat;
	
	public ChercheObjet(MonsterDatabase baseDeDonnees) {
		resultat = baseDeDonnees.extractMonsters()
					.stream()
					.filter(monstre -> !monstre.nomDrop.equals(""))
					.filter(monstre -> !monstre.nomDrop.equals(" "))
					.flatMap(monstre -> monstre.combat.fonds.stream().map(zone -> new String[] {monstre.nomDrop, zone, monstre.nom}))
					.map(Ensemble::new)
					.sorted()
					.distinct()
					.map(Ensemble::toString)
					.collect(Collectors.joining("\n"));
	}
	
	public String toString() {
		return resultat;
	}
	
	
	private static class Ensemble implements Comparable<Ensemble>{
		
		private String[] donnees;

		public Ensemble(String[] donnees) {
			this.donnees = donnees;
		}
		
		public String toString() {
			return donnees[0]+";"+donnees[1]+";"+donnees[2];
		}
		
		@Override
		public int hashCode() {
			return Arrays.deepHashCode(new Object[] { donnees });
		}
		
		@Override
		public boolean equals(Object object) {
			if (object instanceof Ensemble) {
				Ensemble that = (Ensemble) object;
				return Arrays.deepEquals(this.donnees, that.donnees);
			}
			return false;
		}

		@Override
		public int compareTo(Ensemble arg0) {
			for (int i = 0 ; i != 3 ; i++) {
				int cmp = donnees[i].compareTo(arg0.donnees[i]);
				
				if (cmp != 0) {
					return cmp;
				}
			}
			
			return 0;
		}
	}
}
