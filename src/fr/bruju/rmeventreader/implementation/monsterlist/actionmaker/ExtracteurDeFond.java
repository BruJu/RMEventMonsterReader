package fr.bruju.rmeventreader.implementation.monsterlist.actionmaker;

import java.util.Collection;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExtChangeVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExtCondition;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ModuleExecVariables;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Comparateur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Condition;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ValeurGauche;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Variable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Condition.CondInterrupteur;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionOnBattleId;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionPassThrought;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Combat;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;

public class ExtracteurDeFond extends StackedActionMaker<Combat> implements ModuleExecVariables {
	/* ==================
	 * StackedActionMaker
	 * ================== */

	/** Numéro de la variable contenant le fond */
	private final int VARIABLE_ID_FOND;
	/** Numéro de la variable contenant l'id du combat */
	private final int VARIABLE_IDCOMBAT;
	
	/** Condition sur un switch ignoré */
	private final int SWITCH_IGNORE1;
	/** Condition sur un switch ignoré */
	private final int SWITCH_IGNORE2;
	
	/**	Base de données de monstre */
	private MonsterDatabase bdd;


	/**
	 * Instancie le faiseur d'action avec la base de données à compléter
	 * 
	 * @param database La base de données à compléter
	 */
	public ExtracteurDeFond(MonsterDatabase database) {
		this.bdd = database;

		VARIABLE_IDCOMBAT = database.contexte.getVariable("MonsterDB_IDCombat");
		VARIABLE_ID_FOND  = database.contexte.getVariable("MonsterDB_FondCombat");
		SWITCH_IGNORE1  = database.contexte.getVariable("ExtacteurFond_IgnoreSwitch1");
		SWITCH_IGNORE2  = database.contexte.getVariable("ExtacteurFond_IgnoreSwitch2");
	}
	
	@Override
	protected Collection<Combat> getAllElements() {
		return bdd.extractBattles();
	}
	
	@Override
	public boolean Flot_si(Condition condition) {
		return gstConditions.$(condition);
	}

	@Override
	public void Variables_affecterVariable(ValeurGauche valeurGauche, ValeurDroiteVariable valeurDroite) {
		gstVariables.$(valeurGauche, valeurDroite);
	}

	
	/* =======
	 * Modules
	 * ======= */
	
	private ExtCondition gstConditions = new Conditions();
	private ExtChangeVariable gstVariables = new Variables();

	@Override
	public ModuleExecVariables getExecVariables() {
		return this;
	}
	
	private class Variables implements ExtChangeVariable {
		@Override
		public void affecterVariable(Variable valeurGauche, ValeurFixe valeur) {
			if (valeurGauche.idVariable == VARIABLE_ID_FOND) {
				getElementsFiltres().forEach(c -> c.addFond(valeur.valeur));
			}
		}
	}
	
	private class Conditions implements ExtCondition.VariableEtendu {
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
			if (variable != VARIABLE_IDCOMBAT) {
				conditions.push(new ConditionPassThrought<>());
			} else {
				conditions.push(new ConditionOnBattleId(comparateur, droite.valeur));
			}
			return true;
		}
		
	}
	
	

}
