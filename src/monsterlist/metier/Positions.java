package monsterlist.metier;

import monsterlist.Pair;

public enum Positions {
	
	POS_ID(549, 550, 551),
	POS_NIV(555, 556, 557),
	POS_EXP(574, 575, 576),
	POS_CAPA(557, 558, 559),
	POS_ARGENT(594, 595, 596),
	POS_HP(514, 516, 517),
	POS_FORCE(533, 534, 535),
	POS_DEFENSE(530, 531, 532),
	POS_MAGIE(613, 614, 615),
	POS_ESPRIT(570, 571, 572),
	POS_DEXTERITE(527, 528, 529),
	POS_ESQUIVE(536, 537, 538);

	public static final int NB_MONSTRES_MAX_PAR_COMBAT = 3;
	public static final int TAILLE = Positions.POS_ESQUIVE.ordinal() + 1;
	
	public int[] ids;
	
	Positions(int idMonstre1, int idMonstre2, int idMonstre3) {
		ids = new int[]{idMonstre1, idMonstre2, idMonstre3};
	}
	
	public static Pair<Positions, Integer> searchNumVariable(int variable) {
		for (Positions position : Positions.values()) {
			for (int i = 0 ; i != NB_MONSTRES_MAX_PAR_COMBAT ; i++) {
				if (variable == position.ids[i]) {
					return new Pair<>(position, i);
				}
			}
		}
		
		return null;
	}
}