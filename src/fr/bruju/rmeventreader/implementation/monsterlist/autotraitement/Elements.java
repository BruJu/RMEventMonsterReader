package fr.bruju.rmeventreader.implementation.monsterlist.autotraitement;

import fr.bruju.rmeventreader.implementation.monsterlist.contexte.ContexteElementaire;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;


public class Elements {

	public static void initialiserElements(MonsterDatabase bdd, ContexteElementaire contexte) {
		for (Monstre monstre : bdd.extractMonsters()) {
			monstre.remplir(contexte.getElements(), 0);
			monstre.remplir(contexte.getParties(), false);
		}
	}

	public static void finaliser(MonsterDatabase bdd, ContexteElementaire contexte) {
		for (Monstre monstre : bdd.extractMonsters()) {
			int bonus = monstre.accessInt("Niveau") / 7 * 5;

			monstre.modifier("Physique", v -> v + bonus / 2);

			for (String element : contexte.getElements()) {
				if (!element.equals("Physique")) {
					monstre.modifier(element, v -> v + bonus);
				}
			}
		}
	}
}
