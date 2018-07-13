package monsterlist.metier;

import java.util.ArrayList;
import java.util.List;

import actionner.Operator;
import actionner.ReturnValue;
import actionner.SwitchNumber;
import monsterlist.Condition;

public class MonsterDatabase {
	public final static int POS_ID_COMBAT = 435;
	
	private List<Combat> combatsConnus = new ArrayList<>();
	

	

	

	
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
	
	public List<Combat> extractBattles() {
		return combatsConnus;
	}
	
	
	
	public void afficherLesInfosDunCombat(int idCombat) {
		extractBattles().stream().filter(c -> c.getId() == idCombat).forEach(c -> 
		{
			for (int i = 0 ; i != 3 ; i++) {
				if (c.getMonstre(i) == null)
					continue;
				
				System.out.println(c.getMonstre(i).getBetterDisplay(i));
				
			}
			
		});
	}
	
	public void trouverLesCombatsAvecDesNomsInconnus() {
		extractBattles().stream()
						.filter(battle -> battle.getMonstersStream()
							                 	.filter(m -> m != null && m.name.equals("UNKNOWN_NAME"))
							                 	.findAny()
							                 	.isPresent())
						.forEach(battle -> System.out.println(battle.getString()));
	}


	public Combat getBattleById(int idCombat) {
		for (Combat combat : combatsConnus) {
			if (combat.getId() == idCombat) {
				return combat;
			}
		}
		
		return null;
	}


	public void trouverLesMonstresAvecDesNomsInconnus() {
		this.extractMonsters().stream()
		.filter(m -> m != null && m.name.equals("UNKNOWN_NAME"))
		.forEach(m -> System.out.println("add(" + m.getBattleId() + ", " + m.getIdInBattleOf() + ", 0);"));
		;
		
	}


	public void removeBattle(int i) {
		for (Combat combat : combatsConnus) {
			if (combat.getId() == i) {
				combatsConnus.remove(combat);
				return;
			}
		}
	}
	
	
}
