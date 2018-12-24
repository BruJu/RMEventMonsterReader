package fr.bruju.rmeventreader.implementation.monsterlist.autotraitement;

import fr.bruju.rmeventreader.implementation.monsterlist.metier.Combat;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;

/**
 * Action permettant de faire la somme des points de capacités des monstres dans les combats
 * 
 * @author Bruju
 *
 */
public class SommeurDePointsDeCapacites {
	public static void sommer(MonsterDatabase db) {

		for (Combat combat : db.extractBattles()) {
			int total = 0;

			for (Monstre monstre : combat) {
				total += monstre.accessInt("Capacité");
			}

			combat.addGainCapa(total);
		}
	}
}
