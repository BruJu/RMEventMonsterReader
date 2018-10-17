package fr.bruju.rmeventreader.implementation.monsterlist.metier;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import fr.bruju.util.similaire.Key;
import fr.bruju.util.similaire.CollectorBySimilarity;

/**
 * Base de données de monstres regroupant les monstres similaires en tout point minus le combat d'apparition
 * 
 * @author Bruju
 *
 */
public class BDDReduite {
	/**
	 * Association entre clés de monstre et liste des mosntres
	 */
	private Map<Key<Monstre>, List<Monstre>> monstreReduits;

	/** Un monstre pris au hasard pour avoir accés au header de monstre */
	private Monstre unMonstre;
	
	/**
	 * Réduit la liste des monstres en monstres similaires
	 * @param monstres
	 */
	public BDDReduite(Collection<Monstre> monstres) {
		unMonstre = monstres.stream().findAny().get();

		monstreReduits = monstres.stream()
								.collect(new CollectorBySimilarity<>(Monstre::hasher, Monstre::sontSimilaires))
								.getMap();
	}

	/**
	 * Renvoie la représentation en format CSV de 
	 */
	public String getCSV() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(unMonstre.getCSVHeader())
		  .append(";Combats")
		  .append(";Zones");

		Comparator<Entry<Key<Monstre>, List<Monstre>>> comparator = new ComparateurCles();

		monstreReduits.entrySet().stream().sorted(comparator).forEach(entry -> {
			List<Monstre> mv = entry.getValue();
					
			
			sb.append("\n");
			sb.append(mv.get(0).getCSV());
			
			// Id de combats
			sb.append(";[")
			  .append(mv.stream()
					    .map(monstre -> Integer.toString(monstre.getBattleId()))
					    .collect(Collectors.joining(",")))
			  .append("]");
			
			// Zones d'apparitions
			List<String> zonesDapparition = mv.stream().map(monstre -> monstre.combat.fonds)
											.flatMap(Collection::stream)
											.distinct()
											.sorted()
											.collect(Collectors.toList());
			
			sb.append(";").append(zonesDapparition);
			
		});

		return sb.toString();
	}

}
