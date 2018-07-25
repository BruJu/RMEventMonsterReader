package fr.bruju.rmeventreader.implementation.formulatracker.formule.personnage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PersonnageUnifie implements Personnage {
	private final String nom;
	private Map<String, Statistique> statistiques;
	private Set<PersonnageReel> personnages;

	public PersonnageUnifie(Set<PersonnageReel> personnages) {
		this.personnages = personnages;
		this.nom = deduireNom();
		deduireStatistiques();
	}

	private void deduireStatistiques() {
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

	private String deduireNom() {
		StringBuilder sb = new StringBuilder();

		if (commencentTousPar(personnages, "Monstre")) {
			sb.append("Monstre[");

			personnages.stream().map(p -> p.getNom().substring(7, p.getNom().length())).sorted()
					.forEach(numero -> sb.append(numero));

			sb.append("]");
		} else {
			sb.append(personnages.stream().map(p -> p.getNom()).collect(Collectors.joining("/")));
		}
		
		// TODO : Fichier ressource pour les alias
		if (sb.toString().equals("Monstre[123]")) {
			return "Monstre";
		}
		if (sb.toString().equals("Irzyka/Membre2/Membre3/Membre4")) {
			return "Allié";
		}
		if (sb.toString().equals("Membre2/Membre3/Membre4")) {
			return "Allié";
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

	@Override
	public Set<PersonnageReel> getPersonnagesReels() {
		return personnages;
	}
}
