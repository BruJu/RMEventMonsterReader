package monsterlist.autotraitement;

import monsterlist.metier.MonsterDatabase;
import monsterlist.metier.Positions;

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
