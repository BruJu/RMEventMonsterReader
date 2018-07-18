package fr.bruju.rmeventreader.implementation.monsterlist.metier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;

public class MonsterDatabase {
	public final static int POS_ID_COMBAT = 435;

	public static final int POS_BOSSBATTLE = 190;
	
	//private List<Combat> combatsConnus = new ArrayList<>();
	
	private Map<Integer, Combat> combats = new HashMap<>();
	


	/**
	 * Ajoute le combat portant l'id donné si il n'existe pas déjà
	 * @param id Le combat à ajouter
	 */
	public void addCombat(int id) {
		Combat combat = getBattleById(id);
		
		if (combat == null) {
			combats.put(id, new Combat(id));
		}
	}
	

	/**
	 * Renvoie le combat dont l'id est donné
	 * @param idCombat L'id du combat
	 * @return Le combat
	 */
	public Combat getBattleById(int idCombat) {
		return combats.get(idCombat);
	}

	
	/**
	 * Donne une représentation la liste des combats
	 * @return Une chaîne avec la liste des combats
	 */
	public String getString() {
		String s = "";
		
		for (Combat combat : combats.values()) {
			s = s + "\n" + combat.getString();
		}
		
		return s;
	}
	
	public Collection<Monstre> extractMonsters() {
		List<Monstre> monstres = new ArrayList<>();
		
		for (Combat combat : combats.values()) {
			for (int i = 0 ; i != Positions.NB_MONSTRES_MAX_PAR_COMBAT ; i++) {
				if (combat.monstres[i] != null) {
					monstres.add(combat.monstres[i]);
				}
			}
		}
		
		return monstres;
	}
	
	public Collection<Combat> extractBattles() {
		return combats.values();
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




	public void trouverLesMonstresAvecDesNomsInconnus() {
		
		/*
		this.extractMonsters().stream()
		.filter(m -> m != null && m.name.equals("UNKNOWN_NAME"))
		.forEach(m -> System.out.println("add(" + m.getBattleId() + ", " + m.getIdInBattleOf() + ", 0);"));
		;
		*/
	}


	public void removeBattle(int id) {
		combats.remove(id);
	}


	public static void setBossBattle(Collection<Combat> collection) {
		for (Combat combat : collection) {
			combat.declareBossBattle();
		}
	}
	
	
	public String getCSVRepresentationOfBattles() {
		StringBuilder sb = new StringBuilder();
		sb.append(Combat.getCSVHeader());
		
		combats.values().forEach(combat -> {sb.append("\n"); sb.append(combat.getCSV()); } );
		
		return sb.toString();
	}

	public String getCSVRepresentationOfMonsters() {
		StringBuilder sb = new StringBuilder();
		sb.append(Monstre.getCSVHeader(true));
		
		combats.values().stream()
					.flatMap(combat -> combat.getMonstersStream())
					.filter(monstre -> monstre != null)
					.forEach(monstre -> {sb.append("\n"); sb.append(monstre.getCSV(true)); });
		
		return sb.toString();
	}
	
	
	
	public static void setVariable(Collection<Combat> collection, Variable variable, Operator operator, ValeurFixe returnValue) {
		for (Combat combat : collection) {
			combat.applyModificator(variable.get(), operator, returnValue.get());
		}
	}
	
}
