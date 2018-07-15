package fr.bruju.rmeventreader.implementation.monsterlist.autotraitement;

import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Positions;

public class InitialisateurTotal implements ActionAutomatique {
	private MonsterDatabase db;
	
	public InitialisateurTotal(MonsterDatabase db) {
		this.db = db;
	}
	
	
	@Override
	public void faire() {
		db.extractBattles()
		  .forEach(battle -> battle.getMonstersStream()
									.filter(m -> m != null)
									.forEach(m -> {
										battle.addGainCapa(m.get(Positions.POS_CAPA));
									})
				  );
	}

}
