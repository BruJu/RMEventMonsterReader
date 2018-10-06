package fr.bruju.rmeventreader.implementation.monsterlist.actionmaker;

import java.util.Collection;

import fr.bruju.rmdechiffreur.controlleur.ExtChangeVariable;
import fr.bruju.rmdechiffreur.controlleur.ExtCondition;
import fr.bruju.rmdechiffreur.modele.Comparateur;
import fr.bruju.rmdechiffreur.modele.ValeurFixe;
import fr.bruju.rmdechiffreur.modele.Variable;
import fr.bruju.rmdechiffreur.modele.Condition.CondInterrupteur;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionOnBattleId;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionPassThrought;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Combat;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;

public class ExtracteurDeFond extends ExecuteurAFiltre<Combat> implements ExtChangeVariable, ExtCondition {
	private static final int VARIABLE_ID_FOND = 1430;
	private static final int VARIABLE_ID_COMBAT = 435;
	private static final int SWITCH_IGNORE1 = 478;
	private static final int SWITCH_IGNORE2 = 1089;
	
	/* ==================
	 * StackedActionMaker
	 * ================== */

	/** Base de données de monstres */
	private MonsterDatabase bdd;

	/**
	 * Instancie le faiseur d'action avec la base de données à compléter
	 * 
	 * @param database La base de données à compléter
	 */
	public ExtracteurDeFond(MonsterDatabase database) {
		this.bdd = database;
	}

	@Override
	protected Collection<Combat> getAllElements() {
		return bdd.extractBattles();
	}
	
	@Override
	public boolean getBooleenParDefaut() {
		return false;
	}
	
	/* ===========
	 * EXTVARIABLE
	 * =========== */

	@Override
	public void affecterVariable(Variable valeurGauche, ValeurFixe valeur) {
		if (valeurGauche.idVariable == VARIABLE_ID_FOND) {
			getElementsFiltres().forEach(c -> c.addFond(valeur.valeur));
		}
	}
	
	/* ============
	 * EXTCONDITION
	 * ============ */

	@Override
	public boolean interrupteur(CondInterrupteur cond) {
		int number = cond.interrupteur;

		if (number == SWITCH_IGNORE1 || number == SWITCH_IGNORE2) {
			return false;
		}

		conditions.push(new ConditionPassThrought<>());
		return true;
	}

	@Override
	public boolean variableVariable(int variable, Comparateur comparateur, Variable droite) {
		conditions.push(new ConditionPassThrought<>());
		return true;
	}

	@Override
	public boolean variableFixe(int variable, Comparateur comparateur, ValeurFixe droite) {
		if (variable != VARIABLE_ID_COMBAT) {
			conditions.push(new ConditionPassThrought<>());
		} else {
			conditions.push(new ConditionOnBattleId(comparateur, droite.valeur));
		}
		return true;
	}
}
