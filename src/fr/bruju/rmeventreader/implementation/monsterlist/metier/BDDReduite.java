package fr.bruju.rmeventreader.implementation.monsterlist.metier;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BDDReduite {
	public List<MonstreReduit> monstresReduits = new ArrayList<>();

	public BDDReduite(MonsterDatabase db) {
		Map<Long, List<Monstre>> collected = db.extractMonsters()
		  .stream()
		  .collect(Collectors.groupingBy(m -> m.hasher()));
		
		
		collected.entrySet()
		         .stream()
		         .map(entree -> new MonstreReduit(entree.getValue()))
		         .forEach(monstresReduits::add);
	}

	public String getCSV() {
		StringBuilder sb = new StringBuilder();

		sb.append(MonstreReduit.getCSVHeader());

		monstresReduits.sort(new Comparator<MonstreReduit>() {

			@Override
			public int compare(MonstreReduit arg0, MonstreReduit arg1) {
				if (arg0.monstre.getId() < arg1.monstre.getId()) {
					return -1;
				}
				if (arg0.monstre.getId() > arg1.monstre.getId()) {
					return 1;
				}

				return 0;
			}

		});

		monstresReduits.forEach(mr -> {
			sb.append("\n");
			sb.append(mr.getCSV());
		});

		return sb.toString();
	}

	public static BDDReduite generate(MonsterDatabase baseDeDonnees) {
		return new BDDReduite(baseDeDonnees);
	}

}
