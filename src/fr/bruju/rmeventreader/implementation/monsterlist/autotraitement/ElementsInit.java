package fr.bruju.rmeventreader.implementation.monsterlist.autotraitement;

import fr.bruju.rmeventreader.implementation.monsterlist.contexte.ContexteElementaire;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;

/**
 * Classe exécutée avant la première classe d'analyser élémentaire pour créer les données
 * 
 * @author Bruju
 *
 */
public class ElementsInit {
	public static void initialiserElements(MonsterDatabase bdd, ContexteElementaire contexte) {
		bdd.extractMonsters().forEach(monstre -> {
			monstre.remplir(contexte.getElements(), 0);
			monstre.remplir(contexte.getParties(), false);
		});
	}
}
