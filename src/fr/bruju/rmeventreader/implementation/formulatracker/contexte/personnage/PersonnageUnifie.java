package fr.bruju.rmeventreader.implementation.formulatracker.contexte.personnage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PersonnageUnifie implements Personnage {
	private final String nom;
	private Map<String, Statistique> statistiques;

	public PersonnageUnifie(Set<PersonnageReel> personnages) {
		this.nom = deduireNom(personnages);
		deduireStatistiques(personnages);
	}

	private void deduireStatistiques(Set<PersonnageReel> personnages) {
		statistiques = new HashMap<>();

		personnages.stream().map(personnageReel -> personnageReel.getStatistiques())
				.flatMap(map -> map.keySet().stream())
				.forEach(nomStat -> statistiques.putIfAbsent(nomStat, new Statistique(this, nomStat, -1)));
	}

	@Override
	public String getNom() {
		return nom;
	}

	public Map<String, Statistique> getStatistiques() {
		return statistiques;
	}

	private static String deduireNom(Set<PersonnageReel> personnages) {
		StringBuilder sb = new StringBuilder();

		if (commencentTousPar(personnages, "Monstre")) {
			sb.append("Monstre[");

			personnages.stream().map(p -> p.getNom().substring(7, p.getNom().length())).sorted()
					.forEach(numero -> sb.append(numero));

			sb.append("]");
		} else {
			sb.append(personnages.stream().map(p -> p.getNom()).collect(Collectors.joining("/")));
		}

		return sb.toString();
	}

	private static boolean commencentTousPar(Set<PersonnageReel> personnages, String debut) {
		for (PersonnageReel personnage : personnages) {
			if (!personnage.getNom().startsWith(debut)) {
				return false;
			}
		}

		return true;
	}
}
