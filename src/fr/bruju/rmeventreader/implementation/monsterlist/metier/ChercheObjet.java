package fr.bruju.rmeventreader.implementation.monsterlist.metier;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChercheObjet {

	public static String chercheObjet(MonsterDatabase baseDeDonnees) {
		return baseDeDonnees.extractMonsters()
				.stream()
				.filter(monstre -> !monstre.nomDrop.equals("") && !monstre.nom.equals(" "))
				.flatMap(ChercheObjet::miseEnFluxDesDrop)
				.sorted()
				.distinct()
				.map(Ensemble::toString)
				.collect(Collectors.joining("\n"));
	}

	private static Stream<Ensemble> miseEnFluxDesDrop(Monstre monstre) {
		return monstre.combat.fonds.stream().map(zone -> new Ensemble(monstre.nomDrop, zone, monstre.nom));
	}

	private static class Ensemble implements Comparable<Ensemble> {
		private final String drop;
		private final String zone;
		private final String nom;

		public Ensemble(String drop, String zone, String nom) {
			this.drop = drop;
			this.zone = zone;
			this.nom = nom;
		}

		public String toString() {
			return drop + ";" + zone + ";" + nom;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Ensemble ensemble = (Ensemble) o;
			return Objects.equals(drop, ensemble.drop) &&
					Objects.equals(zone, ensemble.zone) &&
					Objects.equals(nom, ensemble.nom);
		}

		@Override
		public int hashCode() {
			return Objects.hash(drop, zone, nom);
		}

		@Override
		public int compareTo(Ensemble autre) {
			int cmp;

			if ((cmp = drop.compareTo(autre.drop)) != 0) {
				return cmp;
			}

			if ((cmp = zone.compareTo(autre.zone)) != 0) {
				return cmp;
			}

			return nom.compareTo(autre.nom);
		}
	}
}
