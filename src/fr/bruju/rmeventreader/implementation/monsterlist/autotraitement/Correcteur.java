package fr.bruju.rmeventreader.implementation.monsterlist.autotraitement;

import fr.bruju.rmeventreader.implementation.monsterlist.metier.Combat;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;
import fr.bruju.rmeventreader.utilitaire.LecteurDeFichiersLigneParLigne;

/**
 * Corrige la liste des monstres en utilisant un fichier
 * <p>
 * 
 * Fichier au format :
 * <br>// Commentaire
 * <br>ADDMONSTER 205 0 135
 * <br>DELETEMONSTER 77 2
 * <br>DELETEMONSTER 78 2
 * <br>DELETEMONSTER 357 2
 * <br>DELETEBATTLE 224
 * 
 * @author Bruju
 *
 */
public class Correcteur {
	public static void corrigerBDD(MonsterDatabase bdd, String fichier) {
		LecteurDeFichiersLigneParLigne.lectureFichierRessources(fichier, ligne -> {
			String[] donnees = ligne.split(" ");

			switch (donnees[0]) {
				case "ADDMONSTER":
					ajouterUnMonstre(bdd, donnees);
					break;
				case "DELETEMONSTER":
					supprimerUnMonstre(bdd, donnees);
					break;
				case "DELETEBATTLE":
					supprimerUnCombat(bdd, donnees);
					break;
			}
		});
	}


	/** Action réalisée pour ajouter un monstre */
	private static void ajouterUnMonstre(MonsterDatabase db, String[] arguments) {
		int idCombat  = Integer.parseInt(arguments[1]);
		int idSlot    = Integer.parseInt(arguments[2]);
		int idMonstre = Integer.parseInt(arguments[3]);

		Combat combat = db.getBattleById(idCombat);
		Monstre monstre = combat.getMonstre(idSlot);
		monstre.setId(idMonstre);
	}

	/** Action réalisée pour supprimer un monstre */
	private static void supprimerUnMonstre(MonsterDatabase db, String[] arguments) {
		int idCombat  = Integer.parseInt(arguments[1]);
		int idSlot    = Integer.parseInt(arguments[2]);

		Combat combat = db.getBattleById(idCombat);
		combat.remove(idSlot);
	}

	/** Action réalisée pour supprimer un combat */
	private static void supprimerUnCombat(MonsterDatabase db, String[] arguments) {
		int idCombat  = Integer.parseInt(arguments[1]);

		db.removeBattle(idCombat);
	}
}
