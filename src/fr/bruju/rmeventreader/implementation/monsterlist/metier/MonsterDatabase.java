package fr.bruju.rmeventreader.implementation.monsterlist.metier;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.ReturnValue;
import fr.bruju.rmeventreader.actionmakers.donnees.rework.Variable;

public class MonsterDatabase {
	public final static int POS_ID_COMBAT = 435;

	public static final int POS_BOSSBATTLE = 190;
	
	private List<Combat> combatsConnus = new ArrayList<>();
	

	

	

	
	public void addCombat(int id) {
		for (Combat combat : combatsConnus) {
			if (combat.id == id) {
				return;
			}
		}
		
		combatsConnus.add(new Combat(id));
	}
	
	
	public static void setVariable(List<Combat> combats, Variable variable, Operator operator, ReturnValue returnValue) {
		
		// TODO : Changer cette méthode pour qu'elle soit plus maintenanble
		
		/*
		if (variable.pointed || variable.numberDebut != variable.numberFin || returnValue.type != ReturnValue.Type.VALUE) {
			throw new RuntimeException("Ne supporte pas les pointeurs");
		}
		
		if (returnValue.value != returnValue.borneMax) {
			throw new RuntimeException("Ne supporte pas les returnValues en random");
		}
		*/
		
		for (Combat combat : combats) {
			combat.applyModificator(variable.get(), operator, returnValue.value);
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


	public static void setBossBattle(List<Combat> combats) {
		for (Combat combat : combats) {
			combat.declareBossBattle();
		}
	}
	
	
	public String getCSVRepresentationOfBattles() {
		StringBuilder sb = new StringBuilder();
		sb.append(Combat.getCSVHeader());
		
		combatsConnus.forEach(combat -> {sb.append("\n"); sb.append(combat.getCSV()); } );
		
		return sb.toString();
	}

	public String getCSVRepresentationOfMonsters() {
		StringBuilder sb = new StringBuilder();
		sb.append(Monstre.getCSVHeader(true));
		
		combatsConnus.stream()
					.flatMap(combat -> combat.getMonstersStream())
					.filter(monstre -> monstre != null)
					.forEach(monstre -> {sb.append("\n"); sb.append(monstre.getCSV(true)); });
		
		return sb.toString();
	}
	
}
