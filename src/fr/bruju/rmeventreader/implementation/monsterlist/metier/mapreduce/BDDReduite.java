package fr.bruju.rmeventreader.implementation.monsterlist.metier.mapreduce;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;
import fr.bruju.util.similaire.Cle;
import fr.bruju.util.similaire.GroupeurDeSimilaires;

public class BDDReduite {
	private Map<Cle<Monstre>, List<Monstre>> monstreReduits;

	public BDDReduite(Collection<Monstre> monstres) {

		GroupeurDeSimilaires<Monstre> collecteur = new GroupeurDeSimilaires<Monstre>(Monstre::hasher,
				Monstre::sontSimilaires);

		monstreReduits = monstres.stream().collect(collecteur).getMap();

	}

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
