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

		
		
		CollectorBySimilarity<Monstre> collecteur = new CollectorBySimilarity<Monstre>(Monstre::hasher,
				Monstre::sontSimilaires);

		monstreReduits = monstres.stream().collect(collecteur).getMap();

	}

	/**
	 * Renvoie la représentation en format CSV de 
	 */
	public String getCSV() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(unMonstre.getCSVHeader()).append(";Zones");

		Comparator<Entry<Key<Monstre>, List<Monstre>>> comparator = new ComparateurCles();

		monstreReduits.entrySet().stream().sorted(comparator).forEach(entry -> {
			List<Monstre> mv = entry.getValue();

			List<String> zonesDapparition = mv.stream().map(monstre -> monstre.combat.fonds)
											.flatMap(fond -> fond.stream())
											.distinct()
											.sorted()
											.collect(Collectors.toList());
					
			
			sb.append("\n");
			sb.append(mv.get(0).getCSV() + ";");
			sb.append("[").append(mv.stream().map(monstre -> monstre.getBattleId()).map(nombre -> nombre.toString())
					.collect(Collectors.joining(","))).append("]");
			sb.append(";");
			
			sb.append(zonesDapparition);
			
		});

		return sb.toString();
	}

}
