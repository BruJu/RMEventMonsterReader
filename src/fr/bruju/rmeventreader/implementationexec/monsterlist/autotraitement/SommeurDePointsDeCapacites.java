package fr.bruju.rmeventreader.implementationexec.monsterlist.autotraitement;

import fr.bruju.rmeventreader.implementationexec.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.implementationexec.monsterlist.metier.Monstre;

/**
 * Action permettant de faire la somme des points de capacités des monstres dans les combats
 * 
 * @author Bruju
 *
 */
public class SommeurDePointsDeCapacites implements Runnable {
	/** La base de données */
	private MonsterDatabase db;

	/**
	 * Crée un sommeur de points de capacités pour la base de données donnée
	 * 
	 * @param db La base de donnée
	 */
	public SommeurDePointsDeCapacites(MonsterDatabase db) {
		this.db = db;
	}

	@Override
	public void run() {
		db.extractBattles().forEach(battle -> battle.getMonstersStream().forEach(m -> {
			battle.addGainCapa(m.accessInt(Monstre.STATS).get("Capacité"));
		}));
	}
}
