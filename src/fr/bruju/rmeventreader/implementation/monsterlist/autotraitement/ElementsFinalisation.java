package fr.bruju.rmeventreader.implementation.monsterlist.autotraitement;

import fr.bruju.rmeventreader.implementation.monsterlist.contexte.ContexteElementaire;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;

/**
 * Finaliser l'extraction des faiblesses élémentaires en appliquant un calcul pour réhausser les faiblesses par rapport
 * au niveau.
 * 
 * @author Bruju
 *
 */
public class ElementsFinalisation {
	public static void finaliser(MonsterDatabase bdd, ContexteElementaire contexte) {
		bdd.extractMonsters().forEach(monstre -> {
			int bonus = monstre.accessInt("Niveau") / 7 * 5;

			monstre.modifier("Physique", v -> v + bonus / 2);

			for (String element : contexte.getElements()) {
				if (!element.equals("Physique")) {
					monstre.modifier(element, v -> v + bonus);
				}
			}
		});
	}
}
