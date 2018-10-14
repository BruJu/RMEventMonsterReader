package fr.bruju.rmeventreader.implementation.monsterlist.actionmaker;

import java.util.Collection;

import fr.bruju.rmdechiffreur.controlleur.ExtChangeVariable;
import fr.bruju.rmdechiffreur.controlleur.ExtCondition;
import fr.bruju.rmdechiffreur.modele.Comparateur;
import fr.bruju.rmdechiffreur.modele.OpMathematique;
import fr.bruju.rmdechiffreur.modele.ValeurFixe;
import fr.bruju.rmdechiffreur.modele.Variable;
import fr.bruju.rmdechiffreur.modele.Condition.CondInterrupteur;
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
public class MonsterDatabaseMaker extends ExecuteurAFiltre<Combat> implements ExtCondition, ExtChangeVariable {
	/* ==================
	 * StackedActionMaker
	 * ================== */

	/** Variable contenant le numéro du combat */
	private final int POS_ID_COMBAT = 435;

	/** Interrupteur contenant l'information si c'est un combat de boss */
	private final int POS_BOSSBATTLE = 190;

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
	public int interrupteur(CondInterrupteur condInterrupteur) {
		if (condInterrupteur.interrupteur == 509) {
			conditions.push(new ConditionPassThrought<Combat>());
			return 0;
		}

		if (condInterrupteur.interrupteur == POS_BOSSBATTLE) {
			conditions.push(new ConditionEstUnBoss(condInterrupteur.etat));
			return 0;
		}
		
		return 3;
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
	public int variableFixe(int idVariable, Comparateur comparateur, ValeurFixe droite) {
		if (idVariable != POS_ID_COMBAT)
			return 3;

		conditions.push(new ConditionOnBattleId(comparateur, droite.valeur));
		
		if (comparateur == Comparateur.IDENTIQUE) {
			database.addCombat(droite.valeur);
		}

		return 0;
	}

	@Override
	public boolean getBooleenParDefaut() {
		return false;
	}
}
