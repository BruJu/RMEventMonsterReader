package fr.bruju.rmeventreader.implementation.formulatracker.contexte.personnage;

import java.util.Set;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.implementation.formulareader.model.PersonnageReel;

public class PersonnageUnifie implements Personnage {
	private final String nom;
	
	public PersonnageUnifie(Set<PersonnageReel> personnages) {
		this.nom = deduireNom(personnages);
	}

	@Override
	public String getNom() {
		return nom;
	}
	
	

	private static String deduireNom(Set<PersonnageReel> personnages) {
		StringBuilder sb = new StringBuilder();
		
		if (commencentTousPar(personnages, "Monstre")) {
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
