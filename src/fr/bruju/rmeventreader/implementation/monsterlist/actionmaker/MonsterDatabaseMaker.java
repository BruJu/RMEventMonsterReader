package fr.bruju.rmeventreader.implementation.monsterlist.actionmaker;

import java.util.Collection;

import fr.bruju.rmeventreader.actionmakers.controlleur.ExtChangeVariable;
import fr.bruju.rmeventreader.actionmakers.controlleur.ExtCondition;
import fr.bruju.rmeventreader.actionmakers.modele.Comparateur;
import fr.bruju.rmeventreader.actionmakers.modele.OpMathematique;
import fr.bruju.rmeventreader.actionmakers.modele.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.modele.Variable;
import fr.bruju.rmeventreader.actionmakers.modele.Condition.CondInterrupteur;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionEstUnBoss;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionOnBattleId;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionPassThrought;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Combat;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;
import fr.bruju.rmeventreader.utilitaire.Pair;

/**
 * Action Maker qui crée des combats et rempli dedans les statistiques des monstres
 * 
 * @author Bruju
 *
 */
public class MonsterDatabaseMaker extends StackedActionMaker<Combat>
		implements ExtCondition.$, ExtChangeVariable.$ {
	/* ==================
	 * StackedActionMaker
	 * ================== */

	/** Variable contenant le numéro du combat */
	private final int POS_ID_COMBAT;

	/** Interrupteur contenant l'information si c'est un combat de boss */
	private final int POS_BOSSBATTLE;

	/**
	 * Base de données
	 */
	private MonsterDatabase database;

	/**
	 * Construit un MonsterDatabaseMaker
	 * 
	 * @param monsterDatabase La base de données à remplir
	 */
	public MonsterDatabaseMaker(MonsterDatabase monsterDatabase) {
		database = monsterDatabase;

		POS_ID_COMBAT = database.contexte.getVariable("MonsterDB_IDCombat");
		POS_BOSSBATTLE = database.contexte.getVariable("MonsterDB_BossBattle");
	}

	@Override
	protected Collection<Combat> getAllElements() {
		return database.extractBattles();
	}

	/* =======
	 * Actions
	 * ======= */

	// Switch


	@Override
	public boolean interrupteur(CondInterrupteur condInterrupteur) {
		if (condInterrupteur.interrupteur == 509) {
			conditions.push(new ConditionPassThrought<Combat>());
			return true;
		}

		if (condInterrupteur.interrupteur == POS_BOSSBATTLE) {
			conditions.push(new ConditionEstUnBoss(condInterrupteur.etat));
			return true;
		}
		
		return false;
	}

	@Override
	public void changeSwitch(Variable interrupteur, boolean nouvelleValeur) {
		int numeroInterrupteur = interrupteur.idVariable;

		if (numeroInterrupteur == POS_BOSSBATTLE) {
			getElementsFiltres().forEach(combat -> combat.declareBossBattle());
		} else {
			Pair<Integer, String> monstreTouche = database.contexte.getPropriete(numeroInterrupteur);

			if (monstreTouche == null) {
				return;
			}

			getElementsFiltres().stream()
					.map(combat -> combat.getMonstre(monstreTouche.getLeft(), true))
					.forEach(monstre -> monstre.accessBool(Monstre.PROPRIETES).set(monstreTouche.getRight(), nouvelleValeur));
		}
	}

	@Override
	public void affecterVariable(Variable valeurGauche, ValeurFixe valeurDroite) {
		getElementsFiltres().forEach(combat -> combat.applyModificator(valeurGauche.idVariable, valeurDroite.valeur));
	}

	@Override
	public void changerVariable(Variable valeurGauche, OpMathematique operateur, ValeurFixe valeurDroite) {
		getElementsFiltres().forEach(combat -> combat.applyModificator(valeurGauche.idVariable, operateur, valeurDroite.valeur));
	}

	@Override
	public boolean variableFixe(int idVariable, Comparateur comparateur, ValeurFixe droite) {
		if (idVariable != POS_ID_COMBAT)
			return false;

		conditions.push(new ConditionOnBattleId(comparateur, droite.valeur));
		
		if (comparateur == Comparateur.IDENTIQUE) {
			database.addCombat(droite.valeur);
		}

		return true;
	}
}
