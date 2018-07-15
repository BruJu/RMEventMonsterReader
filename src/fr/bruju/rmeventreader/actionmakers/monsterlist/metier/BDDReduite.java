package fr.bruju.rmeventreader.actionmakers.monsterlist.metier;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BDDReduite {
	public List<MonstreReduit> monstresReduits = new ArrayList<>();

	public static BDDReduite generate(MonsterDatabase db) {
		return db.extractMonsters().stream().map(monstre -> new BDDReduite(monstre)).reduce(new BDDReduite(),
				(a, b) -> new BDDReduite(a, b));
	}

	public BDDReduite() {
	}

	public BDDReduite(Monstre monstre) {
		monstresReduits.add(new MonstreReduit(monstre));
	}

	public BDDReduite(BDDReduite a, BDDReduite b) {
		a.monstresReduits.forEach(mr -> monstresReduits.add(mr.clone()));

		b.monstresReduits.forEach(mr -> {
			for (MonstreReduit present : monstresReduits) {
				if (MonstreReduit.sontIdentiques(mr, present)) {
					present.recoit(mr);
					return;
				}

			}

			monstresReduits.add(mr.clone());
		});
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

}
