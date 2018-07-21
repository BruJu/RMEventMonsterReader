package fr.bruju.rmeventreader.implementation.formulareader.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class PersonnageUnifie {
	
	/**
	 * Regroupements connus
	 */
	private Map<Set<PersonnageReel>, PersonnageGroupe> groupements = new HashMap<>();
	
	/**
	 * Donne le regroupement de personnages
	 * @param personnage1 Personnage à regrouper
	 * @param personnage2 Personnage à regrouper
	 * @return Un meta personnage qui regroupe les autres
	 */
	public Personnage get(Personnage personnage1, Personnage personnage2) {
		Set<PersonnageReel> personnages = new TreeSet<>();
		
		personnage1.desunifier().forEach(personnages::add);
		personnage2.desunifier().forEach(personnages::add);
		
		return get(personnages);
	}
	
	/**
	 * Donne le regroupement de personnages
	 * @param personnages La liste des personnages à regrouper
	 * @return Un meta personnage qui regroupe les autres
	 */
	public Personnage get(Set<PersonnageReel> personnages) {
		PersonnageGroupe perso = groupements.get(personnages);
		
		if (perso == null) {
			perso = creerPersonnage(personnages);
			groupements.put(personnages, perso);
		}
		
		return perso;
	}
	
	private PersonnageGroupe creerPersonnage(Set<PersonnageReel> personnages) {
		StringBuilder sb = new StringBuilder();
		
		if (commencentTousParMonstre(personnages)) {
			sb.append("Monstre[");
			
			personnages.stream()
						.map(p -> p.getNom().substring(7, p.getNom().length()))
						.sorted()
						.forEach(numero -> sb.append(numero));
								
			sb.append("]");
		} else {
			sb.append(personnages.stream()
						.map(p -> p.getNom())
						.collect(Collectors.joining("/")));
		}
		
		return new PersonnageGroupe(sb.toString(), personnages);
	}
	
	
	private boolean commencentTousParMonstre(Set<PersonnageReel> personnages) {
		for (PersonnageReel personnage : personnages) {
			if (!personnage.getNom().startsWith("Monstre")) {
				return false;
			}
		}
		
		return true;
	}


	// Singleton

	private static PersonnageUnifie instance;

	private PersonnageUnifie() {
	}

	public static PersonnageUnifie getInstance() {
		if (null == instance) {
			instance = new PersonnageUnifie();
		}
		return instance;
	}
}
