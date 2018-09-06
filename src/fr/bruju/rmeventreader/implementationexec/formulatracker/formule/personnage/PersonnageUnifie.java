package fr.bruju.rmeventreader.implementationexec.formulatracker.formule.personnage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Représente un ensemble de personnages représenté par un meta personnage
 * @author Bruju
 *
 */
public class PersonnageUnifie implements Personnage {
	/* =================
	 * PERSONNAGE UNIFIE
	 * ================= */
	
	/** Nom du personnage */
	private final String nom;
	/** Association statistiques */
	private Map<String, Statistique> statistiques;
	/** Association propriétés */
	private Map<String, Statistique> proprietes;
	/** Liste des personnages représentés */
	private Set<PersonnageReel> personnages;

	/**
	 * Crée un personnage unifié à partir d'un ensemble de personnages réels
	 * @param personnages L'ensemble des personnages à représenter
	 */
	public PersonnageUnifie(Set<PersonnageReel> personnages) {
		this.personnages = personnages;
		this.nom = deduireNom();
		deduireStatistiques();
	}

	/**
	 * Déduit les statistiques nécessaires pour ce meta personnage
	 */
	private void deduireStatistiques() {
		statistiques = new HashMap<>();
		proprietes = new HashMap<>();

		personnages.stream().map(personnageReel -> personnageReel.getStatistiques())
				.flatMap(map -> map.keySet().stream())
				.forEach(nomStat -> statistiques.putIfAbsent(nomStat, new Statistique(this, nomStat, -1)));
		

		personnages.stream().map(personnageReel -> personnageReel.getProprietes())
				.flatMap(map -> map.keySet().stream())
				.forEach(nomStat -> proprietes.putIfAbsent(nomStat, new Statistique(this, nomStat, -1)));
	}
	
	/**
	 * Déduit le nom du personnage unifié
	 * @return Le nom du personnage unifié
	 */
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
	
	/**
	 * Renvoie vrai si tous les personnages de l'ensemble commencent par le mot début
	 */
	private static boolean commencentTousPar(Set<PersonnageReel> personnages, String debut) {
		for (PersonnageReel personnage : personnages) {
			if (!personnage.getNom().startsWith(debut)) {
				return false;
			}
		}

		return true;
	}

	/* ==========
	 * PERSONNAGE
	 * ========== */
	
	@Override
	public String getNom() {
		return nom;
	}

	@Override
	public Map<String, Statistique> getStatistiques() {
		return statistiques;
	}

	@Override
	public Map<String, Statistique> getProprietes() {
		return proprietes;
	}

	@Override
	public Set<PersonnageReel> getPersonnagesReels() {
		return personnages;
	}
}
