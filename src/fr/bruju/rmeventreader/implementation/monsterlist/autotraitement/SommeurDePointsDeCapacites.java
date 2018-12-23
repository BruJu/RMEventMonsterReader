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
		db.extractBattles().forEach(battle -> battle.getMonstersStream().forEach(m -> {
			battle.addGainCapa(m.accessInt("Capacité"));
		}));
	}
}
