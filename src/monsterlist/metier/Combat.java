package monsterlist.metier;

import java.util.Arrays;
import java.util.stream.Stream;

import actionner.Operator;
import monsterlist.Pair;

public class Combat {
	Monstre[] monstres;
	int id;
	
	public int gainExp = 0;
	private int gainCapa = 0;
	
	boolean bossBattle = false;
	
	
	public void addGainCapa(int value) {
		gainCapa += value;
	}
	
	
	public int getCapa() {
		return gainCapa;
	}
	
	public void declareBossBattle() {
		bossBattle = true;
	}
	
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
		
		
		String s;
		
		if (this.bossBattle) {
			s = "=== Boss " + id;
		} else {
			s = "=== Combat " + id;
		}
		
		s = s + " ; CAPA = " + this.gainCapa + " ; EXP = " + this.gainExp;
		
		for (int i = 0 ; i != 3 ; i++) {
			if (monstres[i] == null)
				continue;
			
			s = s + "\n" + i + ";" + monstres[i].getString();
		}
		return s;
		
	}
	
	public Stream<Monstre> getMonstersStream() {
		return Arrays.stream(monstres);
	}

	public Monstre getMonstre(int i) {
		return monstres[i];
	}

	public void remove(int idSlot) {
		monstres[idSlot] = null;
	}


	public boolean isBossBattle() {
		return bossBattle;
	}
	

	public String getCSV() {
		return this.getId() + ";" + this.gainExp + ";" + this.gainCapa + ";" + ((this.isBossBattle()) ? "Boss" : "");
	}

	public static String getCSVHeader() {
		return "ID;EXP;CAPA;BOSS";
	}
	
}