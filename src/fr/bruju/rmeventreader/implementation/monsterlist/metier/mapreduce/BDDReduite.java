package fr.bruju.rmeventreader.implementation.monsterlist.metier.mapreduce;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonstreReduit;

public class BDDReduite {
	private Map<MonstreCle, MonstreValeur> monstreReduits;
	
	public BDDReduite(Collection<Monstre> monstres) {

		monstreReduits = monstres.stream()
				.collect(Collectors.toMap(monstre -> new MonstreCle(monstre), monstre -> new MonstreValeur(monstre),
						(valeur1, valeur2) -> new MonstreValeur(valeur1, valeur2)
						));
		

	}

	public String getCSV() {
		StringBuilder sb = new StringBuilder();

		sb.append(MonstreReduit.getCSVHeader());


		Comparator<Entry<MonstreCle, MonstreValeur>> comparator = new ComparateurCles();
		
		monstreReduits.entrySet().stream()
		.sorted(comparator)
		.forEach(entry -> {
					MonstreCle mr = entry.getKey();
					MonstreValeur mv = entry.getValue();
					
			sb.append("\n");
			sb.append(mr.getCSV() + ";" + mv.getCSV());
		});

		return sb.toString();
	}

}
