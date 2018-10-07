package fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.formule.personnage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

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

		ajouterMaps(stats -> stats.statistiques);
		ajouterMaps(stats -> stats.proprietes);
	}
	
	private void ajouterMaps(Function<StatPerso, Map<String, Statistique>> getter) {
		Map<String, Statistique> thisMap = getter.apply(this);
		
		personnageAssocie.getIndividus()
						 .stream()
						 .map(personnageReel -> getter.apply(personnageReel.getVariablesAssociees()).keySet())
						 .flatMap(Set::stream)
						 .forEach(nomStat -> thisMap.putIfAbsent(nomStat,
								 								 new Statistique(personnageAssocie, nomStat, -1)));
	}
	
	
	
	@Override
	public void ajouterStatistique(String nom, Integer position, boolean estPropriete) {
		if (estPropriete) {
			proprietes.put(nom, new Statistique(personnageAssocie, nom, position));
		} else {
			statistiques.put(nom, new Statistique(personnageAssocie, nom, position));
		}
	}
}
