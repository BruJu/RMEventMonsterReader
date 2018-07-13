package monsterlist.metier;

import java.util.ArrayList;
import java.util.List;

import actionner.Operator;
import actionner.ReturnValue;
import actionner.SwitchNumber;
import monsterlist.Condition;
import monsterlist.Pair;

public class MonsterDatabase {
	
	public final static int POS_ID_COMBAT = 435;
	
	private List<Combat> combatsConnus = new ArrayList<>();
	

	
	public class Monstre {
		
		Combat combat;
		
		int[] stats;
		public String name;
		int drop;
		
		public Monstre(Combat combat) {
			stats = new int[Positions.TAILLE];
			this.combat = combat;
		}
		
		public String getString() {
			String s = "•";
			
			for (int i = 0 ; i != Positions.TAILLE ; i++) {
				s = s + ", " + stats[i];
			}
			
			return s;
		}
		
		public int getId() {
			return this.stats[Positions.POS_ID.ordinal()];
		}

		public int getBattleId() {
			return combat.getId();
		}
		
	}
	
	public class Combat {
		Monstre[] monstres;
		int id;
		
		public int getId() {
			return this.id;
		}
		
		public Combat(int id) {
			this.id = id;
			monstres = new Monstre[3];
		}

		public void applyModificator(int idVariable, Operator operator, int value) {
			Pair<Positions, Integer> paire = Positions.searchNumVariable(idVariable);
			
			if (paire == null)
				return;
			
			Monstre monstre = getMonster(paire.getRight(), operator);
			
			if (monstre == null) {
				return;
			}
			
			int posStat = paire.getLeft().ordinal();
			
			monstre.stats[posStat] = operator.compute(monstre.stats[posStat], value);
		}

		private Monstre getMonster(Integer position, Operator operator) {
			if (monstres[position] == null) {
				if (operator.isAMultiplier())
					return null;
				
				monstres[position] = new Monstre(this);
			}
			
			return monstres[position];
		}
		
		
		public String getString() {
			String s = "=== Combat " + id;
			
			for (int i = 0 ; i != 3 ; i++) {
				if (monstres[i] == null)
					continue;
				
				s = s + "\n" + i + ";" + monstres[i].getString();
			}
			
			
			return s;
			
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
		
		
		// TODO : isRelevant()
		/*
		if (variable.pointed || variable.numberDebut != variable.numberFin || returnValue.type != ReturnValue.Type.VALUE) {
			throw new RuntimeException("Ne supporte pas les pointeurs");
		}
		
		if (returnValue.value != returnValue.borneMax) {
			throw new RuntimeException("Ne supporte pas les returnValues en random");
		}
		*/
		
		for (Combat combat : combats) {
			combat.applyModificator(variable.numberDebut, operator, returnValue.value);
		}
	}
	
	
	
	public String getString() {
		String s = "";
		
		for (Combat combat : combatsConnus) {
			s = s + "\n" + combat.getString();
		}
		
		return s;
	}
	
	public List<Monstre> extractMonsters() {
		List<Monstre> monstres = new ArrayList<>();
		
		for (Combat combat : combatsConnus) {
			
			for (int i = 0 ; i != Positions.NB_MONSTRES_MAX_PAR_COMBAT ; i++) {
				if (combat.monstres[i] != null) {
					monstres.add(combat.monstres[i]);
				}
			}
		}
		
		return monstres;
	}
	
	
}
