package fr.bruju.rmeventreader.implementation.monsterlist.actionmaker;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.IntBinaryOperator;

import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.Condition;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionEstUnBoss;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionFausse;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionOnMembreStat;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionPassThrought;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Combat;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;
import fr.bruju.rmeventreader.rmdechiffreur.controlleur.ExtChangeVariable;
import fr.bruju.rmeventreader.rmdechiffreur.controlleur.ExtCondition;
import fr.bruju.rmeventreader.rmdechiffreur.modele.Comparateur;
import fr.bruju.rmeventreader.rmdechiffreur.modele.OpMathematique;
import fr.bruju.rmeventreader.rmdechiffreur.modele.ValeurFixe;
import fr.bruju.rmeventreader.rmdechiffreur.modele.Variable;
import fr.bruju.rmeventreader.rmdechiffreur.modele.Condition.CondInterrupteur;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

/**
 * Action maker dont le but est de déterminer les gains totaux d'un combat.
 * 
 * @author Bruju
 *
 */
public class FinDeCombat extends StackedActionMaker<Combat> implements ExtCondition, ExtChangeVariable {
	/* ==========
	 * Constantes
	 * ========== */
	/** Variables avec la position des gains d'expérience */
	private final int[] VARIABLES_EXP;
	/** Variable contenant les gains d'exp totaux */
	private final int VARIABLE_GAINEXP;
	/** Switch déclarant un combat de boss */
	private final int SWITCH_BOSS;

	/** Association numéro de variable - sous action maker */
	private Map<Integer, Gestionnaires> associationActionMaker;

	/* ========
	 * Database
	 * ======== */

	@Override
	public void Flot_commentaire(String message) {
		if (message.equals("ENNEMIS LEGENDAIRES")) {
			conditions.push(new ConditionFausse<>());
		}
	}
	
	/**
	 * Base de données
	 */
	private MonsterDatabase db;

	/**
	 * Construit un action maker dont le but est de déterminer les gains totaux d'un combat
	 * 
	 * @param db La base de données
	 */
	public FinDeCombat(MonsterDatabase db) {
		this.db = db;

		VARIABLE_GAINEXP = db.contexte.getVariable("FinDeCombat_GainEXP");
		SWITCH_BOSS = db.contexte.getVariable("FinDeCombat_SwitchBoss");
		VARIABLES_EXP = db.contexte.getListeVariables("EXP");

		initierAssociationActionMaker();
	}

	/**
	 * Met dans la map les comportements à adopter pour certaines variables
	 */
	private void initierAssociationActionMaker() {
		associationActionMaker = new HashMap<>();

		for (int idVariableHP : db.contexte.getListeVariables("HP")) {
			associationActionMaker.put(idVariableHP, new ComportementIgnore());
		}

		int[] variablesCapa = db.contexte.getListeVariables("Capacité");

		for (int i = 0; i != db.contexte.getNbDeMonstres(); i++) {
			associationActionMaker.put(variablesCapa[i], new ComportementCapacite(i));
			associationActionMaker.put(VARIABLES_EXP[i], new ComportementExperience(i));
		}

		associationActionMaker.put(VARIABLE_GAINEXP, new ComportementGlobalExperience());
	}

	@Override
	protected Collection<Combat> getAllElements() {
		return db.extractBattles();
	}

	@Override
	public void affecterVariable(Variable valeurGauche, Variable valeurDroite) {
		Gestionnaires actionMaker = associationActionMaker.get(valeurGauche.idVariable);

		if (actionMaker != null) {
			actionMaker.affecterVariable(valeurGauche, valeurDroite);
		}
	}

	@Override
	public void affecterVariable(Variable valeurGauche, ValeurFixe valeurDroite) {
		Gestionnaires actionMaker = associationActionMaker.get(valeurGauche.idVariable);

		if (actionMaker != null) {
			actionMaker.affecterVariable(valeurGauche, valeurDroite);
		}
	}

	@Override
	public void changerVariable(Variable valeurGauche, OpMathematique operateur, Variable valeurDroite) {
		Gestionnaires actionMaker = associationActionMaker.get(valeurGauche.idVariable);

		if (actionMaker != null) {
			actionMaker.changerVariable(valeurGauche, operateur, valeurDroite);
		}
	}

	@Override
	public void changerVariable(Variable valeurGauche, OpMathematique operateur, ValeurFixe valeurDroite) {
		Gestionnaires actionMaker = associationActionMaker.get(valeurGauche.idVariable);

		if (actionMaker != null) {
			actionMaker.changerVariable(valeurGauche, operateur, valeurDroite);
		}
	}

	@Override
	public boolean interrupteur(CondInterrupteur condInterrupteur) {
		boolean estBoss = condInterrupteur.interrupteur == SWITCH_BOSS;

		if (estBoss) {
			conditions.push(new ConditionEstUnBoss(condInterrupteur.etat));
		}

		return estBoss;
	}

	@Override
	public boolean variableFixe(int variable, Comparateur comparateur, ValeurFixe droite) {
		Gestionnaires actionMaker = associationActionMaker.get(variable);

		if (actionMaker == null)
			return false;

		return actionMaker.variableFixe(variable, comparateur, droite);
	}

	/* ==========
	 * Exceptions
	 * ========== */

	/**
	 * Exceptions jetées lorsqu'une erreur est trouvée dans la classe FinDeCombat
	 */
	private static class FinDeCombatException extends RuntimeException {
		private static final long serialVersionUID = 7773889825006166977L;

		public FinDeCombatException(String message) {
			super("Fin de combat : " + message);
		}
	}

	/* =================
	 * Sous Action Maker
	 * ================= */

	/**
	 * Interface action maker avec une implémentation par défaut pour les traitements de conditions
	 */
	private interface Gestionnaires extends ExtCondition, ExtChangeVariable {
	}

	/**
	 * Comportement lorsqu'une action concernant une variable ignorée est déclenchée
	 */
	private class ComportementIgnore implements Gestionnaires {
		@Override
		public boolean variableFixe(int variable, Comparateur comparateur, ValeurFixe droite) {
			conditions.push(new ConditionPassThrought<Combat>());
			return true;
		}

		@Override
		public boolean getBooleenParDefaut() {
			return false;
		}
	}

	/**
	 * Comportement concernant une capacité
	 */
	private class ComportementCapacite implements Gestionnaires {
		private int positionMonstre;

		public ComportementCapacite(int positionMonstre) {
			this.positionMonstre = positionMonstre;
		}

		@Override
		public void affecterVariable(Variable valeurGauche, ValeurFixe valeurDroite) {
			Collection<Combat> elems = getElementsFiltres();

			if (!elems.isEmpty()) {
				throw new FinDeCombatException("Affectation brute d'une récompense de capa");
			}
		}

		@Override
		public void changerVariable(Variable valeurGauche, OpMathematique operateur, ValeurFixe valeurDroite) {
			getElementsFiltres().forEach(battle -> battle.addGainCapa(valeurDroite.valeur));
		}

		@Override
		public boolean variableFixe(int variable, Comparateur comparateur, ValeurFixe droite) {
			conditions.push(new ConditionOnMembreStat("Capacité", positionMonstre, comparateur, droite.valeur));
			return true;
		}

		@Override
		public boolean getBooleenParDefaut() {
			return false;
		}
	}

	/**
	 * Comportement concernant l'expérience d'un monstre
	 */
	private class ComportementExperience implements Gestionnaires {
		private int positionMonstre;

		public ComportementExperience(int positionMonstre) {
			this.positionMonstre = positionMonstre;
		}

		public boolean variableFixe(int variable, Comparateur comparateur, ValeurFixe droite) {
			conditions.push(new ConditionOnMembreStat("EXP", positionMonstre, comparateur, droite.valeur));
			return true;
		}

		public void affecterVariable(Variable valeurGauche, ValeurFixe valeurDroite) {
			throw new FinDeCombatException("Modification d'une quantité d'exp gagnée");
		}

		public void changerVariable(Variable valeurGauche, OpMathematique operateur, ValeurFixe valeurDroite) {
			throw new FinDeCombatException("Modification d'une quantité d'exp gagnée");
		}

		@Override
		public boolean getBooleenParDefaut() {
			return false;
		}
	}

	/**
	 * Comportement concernant l'expérience totale
	 */
	private class ComportementGlobalExperience implements Gestionnaires {
		@Override
		public boolean variableFixe(int variable, Comparateur comparateur, ValeurFixe droite) {
			conditions.push(new ConditionGainExpTotal(comparateur, droite.valeur));
			return true;
		}

		@Override
		public void affecterVariable(Variable valeurGauche, ValeurFixe valeurDroite) {
			getElementsFiltres().forEach(c -> c.gainExp = valeurDroite.valeur);
		}

		@Override
		public void changerVariable(Variable valeurGauche, OpMathematique operateur, ValeurFixe valeurDroite) {
			getElementsFiltres().forEach(c -> c.gainExp = operateur.calculer(c.gainExp, valeurDroite.valeur));
		}

		@Override
		public void affecterVariable(Variable valeurGauche, Variable valeurDroite) {
			modifierExp(valeurDroite, (a, b) -> b);
		}

		@Override
		public void changerVariable(Variable valeurGauche, OpMathematique operateur, Variable valeurDroite) {
			modifierExp(valeurDroite, operateur::calculer);
		}

		private void modifierExp(Variable valeurDroite, IntBinaryOperator fonctionDApplication) {

			int idMonstre = Utilitaire.getPosition(valeurDroite.idVariable, VARIABLES_EXP);

			if (idMonstre == -1) {
				throw new FinDeCombatException("Modifie un gain d'exp selon une variable qui n'est pas un gain d'exp");
			}

			getElementsFiltres()
					.forEach(c -> c.gainExp = fonctionDApplication.applyAsInt(c.gainExp, expDuMonstre(c, idMonstre)));
		}

		private Integer expDuMonstre(Combat combat, int position) {
			Monstre monstre = combat.getMonstre(position);

			if (monstre == null) {
				return 0;
			} else {
				return monstre.accessInt(Monstre.STATS).get("EXP");
			}
		}

		@Override
		public boolean getBooleenParDefaut() {
			return false;
		}

	}

	private class ConditionGainExpTotal implements Condition<Combat> {
		private Comparateur comparateur;
		private int expDeReference;

		public ConditionGainExpTotal(Comparateur comparateur, int expDeReference) {
			this.comparateur = comparateur;
			this.expDeReference = expDeReference;
		}

		@Override
		public void revert() {
			comparateur = comparateur.oppose;
		}

		@Override
		public boolean filter(Combat element) {
			return comparateur.test(element.gainExp, expDeReference);
		}
	}

	@Override
	public boolean getBooleenParDefaut() {
		return false;
	}
}
