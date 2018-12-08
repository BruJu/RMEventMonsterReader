package fr.bruju.rmeventreader.implementation.monsterlist.metier;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import fr.bruju.util.similaire.Key;
import fr.bruju.util.similaire.CollectorBySimilarity;

/**
 * Base de données de monstres regroupant les monstres similaires en tout point minus le combat d'apparition
 */
public class BDDReduite {
	/** Objet permettant de représenter les monstres */
	private final Serialiseur serialiseur;
	/** Association entre clés de monstre et liste des mosntres */
	private Map<Key<Monstre>, List<Monstre>> monstreReduits;

	/**
	 * Réduit la liste des monstres en monstres similaires
	 * @param monstres Liste des monstres
	 * @param serialiseur Objet de sérialisation des monstres
	 */
	public BDDReduite(Collection<Monstre> monstres, Serialiseur serialiseur) {
		monstreReduits = monstres.stream()
								.collect(new CollectorBySimilarity<>(Monstre::hasher, Monstre::sontSimilaires))
								.getMap();

		this.serialiseur = new Serialiseur(serialiseur);
		this.serialiseur.supprimer("IDCombat");
		this.serialiseur.supprimer("Zones");
	}

	/**
	 * Renvoie la représentation en format CSV de 
	 */
	public String getCSV() {
		StringBuilder sb = new StringBuilder();

		sb.append(serialiseur.getEnTete())
		  .append(";Combats")
		  .append(";Zones");

		Comparator<Entry<Key<Monstre>, List<Monstre>>> comparator = new ComparateurCles();

		monstreReduits.entrySet().stream().sorted(comparator).forEach(entry -> {
			List<Monstre> mv = entry.getValue();

			sb.append("\n");
			sb.append(serialiseur.serialiserMonstre(mv.get(0)));
			
			// Id de combats
			StringJoiner sj = new StringJoiner(",", ";[", "]");

			for (Monstre monstre : mv) {
				sj.add(Integer.toString(monstre.getBattleId()));
			}

			sb.append(sj);

			// Zones d'apparitions
			Set<String> zonesDapparition = new HashSet<>();

			for (Monstre monstre : mv) {
				for (String fond : monstre.combat.fonds) {
					zonesDapparition.add(fond);
				}
			}
			
			sb.append(";").append(zonesDapparition);
		});

		return sb.toString();
	}

}
