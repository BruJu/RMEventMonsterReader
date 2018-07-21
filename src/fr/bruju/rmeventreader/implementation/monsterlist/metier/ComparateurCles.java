package fr.bruju.rmeventreader.implementation.monsterlist.metier;

import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import fr.bruju.util.similaire.Cle;

public class ComparateurCles implements Comparator<Entry<Cle<Monstre>, List<Monstre>>> {
	@Override
	public int compare(Entry<Cle<Monstre>, List<Monstre>> o1, Entry<Cle<Monstre>, List<Monstre>> o2) {
		int id1 = o1.getValue().get(0).getBattleId();
		int id2 = o2.getValue().get(0).getBattleId();

		if (id1 < id2) {
			return -1;
		}
		if (id1 > id2) {
			return 1;
		}

		return 0;
	}
}
