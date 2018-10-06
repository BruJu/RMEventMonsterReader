package fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.formule.personnage;

import java.util.HashMap;
import java.util.Map;

import fr.bruju.rmeventreader.implementation.detectiondeformules._personnages.Groupe;
import fr.bruju.rmeventreader.implementation.detectiondeformules._personnages.Individu;
import fr.bruju.rmeventreader.implementation.detectiondeformules._personnages.Personne;
import fr.bruju.rmeventreader.implementation.detectiondeformules._personnages.VariablesAssociees;

public class StatPerso implements VariablesAssociees {

	public Map<String, Statistique> statistiques;

	public Map<String, Statistique> proprietes;

	private Personne<StatPerso> personnageAssocie;
	
	
	public StatPerso(Individu<StatPerso> personnageAssocie) {
		this.personnageAssocie = personnageAssocie;
		statistiques = new HashMap<>();
		proprietes = new HashMap<>();
	}
	
	public StatPerso(Groupe<StatPerso> personnageAssocie) {
		
		this.personnageAssocie = personnageAssocie;
		statistiques = new HashMap<>();
		proprietes = new HashMap<>();

		personnageAssocie.getIndividus().stream().map(personnageReel -> personnageReel.getVariablesAssociees().statistiques)
				.flatMap(map -> map.keySet().stream())
				.forEach(nomStat -> statistiques.putIfAbsent(nomStat, new Statistique(personnageAssocie, nomStat, -1)));
		

		personnageAssocie.getIndividus().stream().map(personnageReel -> personnageReel.getVariablesAssociees().proprietes)
				.flatMap(map -> map.keySet().stream())
				.forEach(nomStat -> proprietes.putIfAbsent(nomStat, new Statistique(personnageAssocie, nomStat, -1)));

	}
	
	/**
	 * Ajoute une statistique au personnage
	 * @param nom Le nom de la statistique
	 * @param position Le numéro de sa variable
	 */
	public void addStatistique(String nom, int position) {
		statistiques.put(nom, new Statistique(personnageAssocie, nom, position));
	}

	/**
	 * Ajoute une propriété au personnage
	 * @param nom Le nom de la propriété
	 * @param position Le numéro de son interrupteur
	 */
	public void addPropriete(String nom, int position) {
		proprietes.put(nom, new Statistique(personnageAssocie, nom, position));
	}
}
