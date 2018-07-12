package monsterlist;

import java.util.ArrayList;
import java.util.List;

import actionner.Operator;
import actionner.ReturnValue;
import actionner.SwitchNumber;

public class MonsterDatabase {
	
	public final static int POS_ID_COMBAT = 435;
	
	private List<Combat> combatsConnus = new ArrayList<>();
	
	
	public static enum Positions {
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
		
		public static final int TAILLE = Positions.POS_ESQUIVE.ordinal();
		
		public int[] ids;
		
		Positions(int idMonstre1, int idMonstre2, int idMonstre3) {
			ids = new int[]{idMonstre1, idMonstre2, idMonstre3};
		}
		
		public static Pair<Positions, Integer> searchNumVariable(int variable) {
			for (Positions position : Positions.values()) {
				for (int i = 0 ; i != 3 ; i++) {
					if (variable == position.ids[i]) {
						return new Pair<>(position, i);
					}
				}
			}
			
			return null;
		}
	};
	
	
	public class Monstre {
		int[] stats;
		String name;
		int drop;
		
		public Monstre() {
			stats = new int[Positions.TAILLE];
		}
		
	}
	
	public class Combat {
		Monstre[] monstres;
		int id;
		
		public Combat(int id) {
			this.id = id;
			monstres = new Monstre[3];
		}

		public void applyModificator(int idVariable, Operator operator, int value) {
			Pair<Positions, Integer> paire = Positions.searchNumVariable(idVariable);
			
			if (paire == null)
				return;
			
			Monstre monstre = getMonster(paire.getRight());
			int posStat = paire.getLeft().ordinal();
			
			monstre.stats[posStat] = operator.compute(monstre.stats[posStat], value);
		}

		private Monstre getMonster(Integer position) {
			if (monstres[position] == null)
				monstres[position] = new Monstre();
			
			return monstres[position];
		}
	}
	
	public void addCombat(int id) {
		for (Combat combat : combatsConnus) {
			if (combat.id == id) {
				return;
			}
		}
		
		combatsConnus.add(new Combat(id));
	}
	
	
	public List<Combat> filter(List<Condition> conditions) {
		List<Combat> liste = combatsConnus;
		
		for (Condition condition : conditions) {
			liste = condition.filter(liste);
		}
		
		return liste;
	}
	
	
	public static void setVariable(List<Combat> combats, SwitchNumber variable, Operator operator, ReturnValue returnValue) {
		if (variable.pointed || variable.numberDebut != variable.numberFin || returnValue.type != ReturnValue.Type.VALUE) {
			throw new RuntimeException("Ne supporte pas les pointeurs");
		}
		
		if (returnValue.value != returnValue.borneMax) {
			throw new RuntimeException("Ne supporte pas les returnValues en random");
		}
		
		for (Combat combat : combats) {
			combat.applyModificator(variable.numberDebut, operator, returnValue.value);
		}
	}
	
	
	
}
