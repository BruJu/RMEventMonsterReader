package fr.bruju.rmeventreader.implementation.monsterlist.metier;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import fr.bruju.util.similaire.Cle;
import fr.bruju.util.similaire.GroupeurDeSimilaires;

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
	private Map<Cle<Monstre>, List<Monstre>> monstreReduits;

	/**
	 * Réduit la liste des monstres en monstres similaires
	 * @param monstres
	 */
	public BDDReduite(Collection<Monstre> monstres) {

		GroupeurDeSimilaires<Monstre> collecteur = new GroupeurDeSimilaires<Monstre>(Monstre::hasher,
				Monstre::sontSimilaires);

		monstreReduits = monstres.stream().collect(collecteur).getMap();

	}

	/**
	 * Renvoie la représentation en format CSV de 
	 */
	public String getCSV() {
		StringBuilder sb = new StringBuilder();

		sb.append(Monstre.getCSVHeader());

		Comparator<Entry<Cle<Monstre>, List<Monstre>>> comparator = new ComparateurCles();

		monstreReduits.entrySet().stream().sorted(comparator).forEach(entry -> {
			List<Monstre> mv = entry.getValue();

			sb.append("\n");
			sb.append(mv.get(0).getCSV() + ";");
			sb.append("[").append(mv.stream().map(monstre -> monstre.getBattleId()).map(nombre -> nombre.toString())
					.collect(Collectors.joining(","))).append("]");
		});

		return sb.toString();
	}

}
