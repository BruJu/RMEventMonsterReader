package fr.bruju.rmeventreader.implementation.recomposeur.operations.desinjection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.recomposeur.Parametres;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.Personnage;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.Statistique;
import fr.bruju.rmeventreader.implementation.recomposeur.operations.interfaces.IncrementateurDeHeader;
import fr.bruju.rmeventreader.implementation.recomposeur.operations.interfaces.StructureDInjectionDeHeader;

public class PreTraitementDesinjection implements StructureDInjectionDeHeader {
	
	private Map<String, Map<Integer, Integer>> cartesConnues;
	
	public PreTraitementDesinjection(Parametres parametres) {
		cartesConnues = new HashMap<>();
		
		List<String[]> donnees = parametres.getParametres("Desinjection");
		
		donnees.forEach(tableau -> {
			String nomPerso = tableau[0];
			Integer idVariable = Integer.decode(tableau[1]);
			Integer valeur = Integer.decode(tableau[2]);
			
			Map<Integer, Integer> map = cartesConnues.get(nomPerso);
			
			if (map == null) {
				map = new TreeMap<Integer, Integer>();
				cartesConnues.put(nomPerso, map);
			}
			
			map.put(idVariable, valeur);
		});
	}
	
	public Map<Integer, Integer> extraire(Personnage possesseur) {
		return cartesConnues.get(possesseur.getNom());
	}
	
	@Override
	public IncrementateurDeHeader creerIncrementateur(Statistique stat, Algorithme algo) {
		return new Desinjection(algo, extraire(stat.possesseur));
	}

	@Override
	public String getNom() {
		return "Ciblage";
	}
}