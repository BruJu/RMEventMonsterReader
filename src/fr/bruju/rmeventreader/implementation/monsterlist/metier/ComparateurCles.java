package fr.bruju.rmeventreader.implementation.monsterlist.metier;

import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import fr.bruju.util.similaire.Key;

/**
 * Comparateur de clés de monstres selon le premier combat de la liste
 * 
 * @author Bruju
 *
 */
public class ComparateurCles implements Comparator<Entry<Key<Monstre>, List<Monstre>>> {
	@Override
	public int compare(Entry<Key<Monstre>, List<Monstre>> o1, Entry<Key<Monstre>, List<Monstre>> o2) {
		int[] criteresO1 = {o1.getValue().get(0).getId(), o1.getValue().get(0).getBattleId()};
		int[] criteresO2 = {o2.getValue().get(0).getId(), o2.getValue().get(0).getBattleId()};
		
		
		for (int i = 0 ; i != criteresO1.length ; i++) {
			int comparaison = Integer.compare(criteresO1[i], criteresO2[i]);
			
			if (comparaison != 0) {
				return comparaison;
			}
		}
		
		return 0;
	}
}
