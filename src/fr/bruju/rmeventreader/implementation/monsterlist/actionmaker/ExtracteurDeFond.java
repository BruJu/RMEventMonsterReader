package fr.bruju.rmeventreader.implementation.monsterlist.actionmaker;

import java.util.Collection;

import org.apache.commons.lang3.BooleanUtils;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ModuleExecVariables;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Comparateur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Condition;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.FixeVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ValeurGauche;
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

	@Override
	public ModuleExecVariables getExecVariables() {
		return super.getExecVariables();
	}

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
	public void changeVariable(Variable variable, Operator operator, ValeurFixe returnValue) {
		if (variable.idVariable != VARIABLE_ID_FOND) {
			return;
		}
		
		getElementsFiltres().forEach(c -> c.addFond(returnValue.valeur));
	}

	@Override
	public void Variables_affecterVariable(ValeurGauche valeurGauche, ValeurDroiteVariable valeurDroite) {
		valeurGauche.appliquerG(variable -> {
			if (variable.idVariable == VARIABLE_ID_FOND) {
				valeurDroite.appliquerDroite(valeur -> {
					getElementsFiltres().forEach(c -> c.addFond(valeur.valeur));
					return null;
				}, null, null);
			}
			return null;
		}, null, null);
	}

	@Override
	public boolean Flot_si(Condition condition) {
		boolean r = false;
		
		r = r | BooleanUtils.isTrue(condition.appliquerInterrupteur(cond -> this.superfonction(cond)));
		r = r | BooleanUtils.isTrue(condition.appliquerVariable(cond -> condOnVariable(cond.variable, cond.comparateur, cond.valeurDroite)));
		
		return r;
	}

	private boolean condOnVariable(int variable, Comparateur comparateur, FixeVariable valeurDroite) {
		if (variable == VARIABLE_IDCOMBAT) {
			
			valeurDroite.appliquerFV(
					fixe -> {conditions.push(new ConditionOnBattleId(comparateur, fixe.valeur)); return null;},
					v -> {conditions.push(new ConditionPassThrought<>()); return null;}
					);
		} else {
			conditions.push(new ConditionPassThrought<>());
		}
		
		return true;
	}

	public boolean superfonction(CondInterrupteur cond) {
		int number = cond.interrupteur;
		
		
		if (number == this.SWITCH_IGNORE1 || number == this.SWITCH_IGNORE2) {
			return false;
		}
		
		conditions.push(new ConditionPassThrought<>());
		return true;
	}
}
