package fr.bruju.rmeventreader.implementation.monsterlist.metier;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Base de données de monstres et de combats
 * 
 * @author Bruju
 *
 */
public class MonsterDatabase {
	/* ===============
	 * BDD DE MONSTRES 
	 * =============== */
	
	/** Association numéro de combat - combat*/
	private Map<Integer, Combat> combats = new HashMap<>();
	
	/** Contexte de variables*/
	public final Contexte contexte;
	
	/* =======================
	 * MANIPULATION DE COMBATS 
	 * ======================= */
	
	/**
	 * Crée une base de données de monstre avec le contexte
	 * @param contexte Le contexte contenant les associations de variables
	 */
	public MonsterDatabase(Contexte contexte) {
		this.contexte = contexte;
	}

	/**
	 * Ajoute le combat portant l'id donné si il n'existe pas déjà
	 * @param id Le combat à ajouter
	 */
	public void addCombat(int id) {
		combats.putIfAbsent(id, new Combat(contexte, id));
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
	 * Enlève le combat dont l'id est donné
	 */
	public void removeBattle(int id) {
		combats.remove(id);
	}
	
	
	/* ===========
	 * EXTRACTIONS 
	 * =========== */
	
	/**
	 * Donne la liste des combats
	 */
	public Collection<Combat> extractBattles() {
		return combats.values();
	}
	
	/**
	 * Donne la liste de tous les monstres de la base de données
	 */
	public Collection<Monstre> extractMonsters() {
		return combats.values().stream().flatMap(combat -> combat.getMonstersStream()).collect(Collectors.toList());
	}

	
	/* ========
	 * ANALAYSE 
	 * ======== */
	
	/**
	 * Affiche dans la console la liste des combats avec des monstres sans nom
	 */
	public void trouverLesCombatsAvecDesNomsInconnus() {
		extractBattles().stream()
						.filter(battle -> battle.getMonstersStream()
							                 	.filter(m -> m.nom.equals("UNKNOWN_NAME"))
							                 	.findAny()
							                 	.isPresent())
						.forEach(battle -> System.out.println(battle.getString()));
	}
	
	
	/* =========
	 * AFFICHAGE
	 * ========= */

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
	
	/**
	 * Donne une représentation CSV des combats dans leur globalité
	 */
	public String getCSVRepresentationOfBattles() {
		StringBuilder sb = new StringBuilder();
		sb.append(Combat.getCSVHeader());
		
		combats.values().forEach(combat -> sb.append("\n").append(combat.getCSV()));
		
		return sb.toString();
	}

	/**
	 * Donne une représentation CSV des monstres individuellement des combats
	 */
	public String getCSVRepresentationOfMonsters() {
		StringBuilder sb = new StringBuilder();
		sb.append(extractMonsters().stream().findAny().get().getCSVHeader(true));
		
		combats.values().stream()
					.flatMap(combat -> combat.getMonstersStream())
					.forEach(monstre -> {sb.append("\n"); sb.append(monstre.getCSV(true)); });
		
		return sb.toString();
	}
}
