package fr.bruju.rmeventreader.implementation.monsterlist.metier.mapreduce;

import java.util.Comparator;
import java.util.Map.Entry;

public class ComparateurCles implements Comparator<Entry<MonstreCle, MonstreValeur>> {
	@Override
	public int compare(Entry<MonstreCle, MonstreValeur> o1, Entry<MonstreCle, MonstreValeur> o2) {
		int id1 = o1.getKey().getId();
		int id2 = o2.getKey().getId();
		
		if (id1 < id2) {
			return -1;
		}
		if (id1 > id2) {
			return 1;
		}

		return 0;
	}
}
