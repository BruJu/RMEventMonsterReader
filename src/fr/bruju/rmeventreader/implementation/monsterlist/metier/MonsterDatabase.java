package fr.bruju.rmeventreader.implementation.monsterlist.metier;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.implementation.monsterlist.contexte.Contexte;

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
	
	public final Contexte contexte;

	public final Serialiseur serialiseur;
	
	/* =======================
	 * MANIPULATION DE COMBATS 
	 * ======================= */
	
	/**
	 * Crée une base de données de monstre avec le contexte
	 * @param contexte Le contexte contenant les associations de variables
	 */
	public MonsterDatabase(Contexte contexte) {
		this.contexte = contexte;

		serialiseur = new Serialiseur();
		contexte.injecter(serialiseur);
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
		List<Monstre> monstres = new ArrayList<>();

		for (Combat combat : combats.values()) {
			for (Monstre monstre : combat) {
				monstres.add(monstre);
			}
		}

		return monstres;
	}

	/* ======
	 * FILTRE
	 * ====== */
	
	/**
	 * Crée une nouvelle base de données avec uniquement les combats respectant le prédicat donné
	 * @param predicat Le prédicat à respecter
	 * @return Une nouvelle base de données avec les combats filtrés
	 */
	public MonsterDatabase filtrer(Predicate<Combat> predicat) {
		MonsterDatabase nouvelleDb = new MonsterDatabase(contexte);
		
		nouvelleDb.combats = new LinkedHashMap<>();
		
		this.combats.values().stream()
			.filter(predicat)
			.sorted(Comparator.comparingInt(c2 -> c2.id))
			.forEach(c -> nouvelleDb.combats.put(c.id, c));
		
		return nouvelleDb;
	}
	
	
	/* =======
	 * ANALYSE 
	 * ======= */
	
	/**
	 * Donne la liste des monstres dont le nom est UNKNOWN_NAME
	 */
	public List<Monstre> trouverLesMonstresAvecDesNomsInconnus() {
		List<Monstre> monstres = new ArrayList<>();

		for (Monstre monstre : extractMonsters()) {
			if (monstre.nom.equals("UNKNOWN_NAME")) {
				monstres.add(monstre);
			}
		}

		return monstres;
	}
	
	
	/* =========
	 * AFFICHAGE
	 * ========= */

	/**
	 * Donne une représentation la liste des combats
	 * @return Une chaîne avec la liste des combats
	 */
	public String getString() {
		StringBuilder s = new StringBuilder();
		
		for (Combat combat : combats.values()) {
			s.append("\n").append(combat.getString(serialiseur));
		}
		
		return s.toString();
	}
	
	/**
	 * Donne une représentation CSV des combats dans leur globalité
	 */
	public String getCSVRepresentationOfBattles() {
		StringBuilder sb = new StringBuilder();
		sb.append(Combat.getCSVHeader());

		for (Combat combat : combats.values()) {
			sb.append("\n").append(combat.getCSV());
		}
		
		return sb.toString();
	}

	/**
	 * Donne une représentation CSV des monstres individuellement des combats
	 */
	public String getCSVRepresentationOfMonsters() {
		StringBuilder sb = new StringBuilder();

		sb.append(serialiseur.getEnTete());

		for (Combat combat : combats.values()) {
			for (Monstre monstre : combat) {
				sb.append("\n").append(serialiseur.serialiserMonstre(monstre));
			}
		}

		return sb.toString();
	}
}
