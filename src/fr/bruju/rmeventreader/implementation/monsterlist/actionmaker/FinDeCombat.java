package fr.bruju.rmeventreader.implementation.monsterlist.actionmaker;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.IntBinaryOperator;

import fr.bruju.rmeventreader.utilitaire.Utilitaire;
import fr.bruju.rmdechiffreur.controlleur.ExtChangeVariable;
import fr.bruju.rmdechiffreur.controlleur.ExtCondition;
import fr.bruju.rmdechiffreur.modele.Comparateur;
import fr.bruju.rmdechiffreur.modele.OpMathematique;
import fr.bruju.rmdechiffreur.modele.ValeurFixe;
import fr.bruju.rmdechiffreur.modele.Variable;
import fr.bruju.rmdechiffreur.modele.Condition.CondInterrupteur;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.Condition;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionEstUnBoss;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionFausse;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionOnMembreStat;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionPassThrought;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Combat;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;

/**
 * Action maker dont le but est de déterminer les gains totaux d'un combat.
 * 
 * @author Bruju
 *
 */
public class FinDeCombat extends ExecuteurAFiltre<Combat> implements ExtCondition, ExtChangeVariable {
	private static final int VARIABLE_GAIN_EXP_TOTAL = 4976;
	private static final int INTERRUPTEUR_COMBAT_DE_BOSS = 190;
	
	/** Base de données */
	private MonsterDatabase bdd;
	
	/** Variables avec la position des gains d'expérience */
	private Map<Integer, Gestionnaire> sousGestionnaires;
	
	/**
	 * Construit un exécuteur dont le but est de déterminer les gains totaux d'un combat
	 * 
	 * @param bdd La base de données
	 */
	public FinDeCombat(MonsterDatabase bdd) {
		this.bdd = bdd;
		initierAssociationActionMaker();
	}

	@Override
	public boolean getBooleenParDefaut() {
		return false;
	}
	
	@Override
	public void Flot_commentaire(String message) {
		// Interrompt le traitement lorsque le commentaire ENNEMIS LEGENDAIRES est rencontré
		if (message.equals("ENNEMIS LEGENDAIRES")) {
			conditions.push(new ConditionFausse<>());
		}
	}

	/**
	 * Met dans la map les comportements à adopter pour certaines variables
	 */
	private void initierAssociationActionMaker() {
		sousGestionnaires = new HashMap<>();

		for (int idVariableHP : bdd.contexte.getListeVariables("HP")) {
			sousGestionnaires.put(idVariableHP, new ComportementIgnore());
		}

		int[] variablesCapa = bdd.contexte.getListeVariables("Capacité");
		int[] variablesExpe = bdd.contexte.getListeVariables("EXP");

		for (int i = 0; i != Combat.NOMBRE_DE_MONSTRES; i++) {
			sousGestionnaires.put(variablesCapa[i], new ComportementCapacite(i));
			sousGestionnaires.put(variablesExpe[i], new ComportementExperience(i));
		}

		sousGestionnaires.put(VARIABLE_GAIN_EXP_TOTAL, new ComportementGlobalExperience());
	}

	@Override
	protected Collection<Combat> getAllElements() {
		return bdd.extractBattles();
	}

	@Override
	public void affecterVariable(Variable valeurGauche, Variable valeurDroite) {
		Gestionnaire actionMaker = sousGestionnaires.get(valeurGauche.idVariable);

		if (actionMaker != null) {
			actionMaker.affecterVariable(valeurGauche, valeurDroite);
		}
	}

	@Override
	public void affecterVariable(Variable valeurGauche, ValeurFixe valeurDroite) {
		Gestionnaire actionMaker = sousGestionnaires.get(valeurGauche.idVariable);

		if (actionMaker != null) {
			actionMaker.affecterVariable(valeurGauche, valeurDroite);
		}
	}

	@Override
	public void changerVariable(Variable valeurGauche, OpMathematique operateur, Variable valeurDroite) {
		Gestionnaire actionMaker = sousGestionnaires.get(valeurGauche.idVariable);

		if (actionMaker != null) {
			actionMaker.changerVariable(valeurGauche, operateur, valeurDroite);
		}
	}

	@Override
	public void changerVariable(Variable valeurGauche, OpMathematique operateur, ValeurFixe valeurDroite) {
		Gestionnaire actionMaker = sousGestionnaires.get(valeurGauche.idVariable);

		if (actionMaker != null) {
			actionMaker.changerVariable(valeurGauche, operateur, valeurDroite);
		}
	}

	@Override
	public int interrupteur(CondInterrupteur condInterrupteur) {
		boolean estBoss = condInterrupteur.interrupteur == INTERRUPTEUR_COMBAT_DE_BOSS;

		if (estBoss) {
			conditions.push(new ConditionEstUnBoss(condInterrupteur.etat));
		}

		return estBoss ? 0 : 3;
	}

	@Override
	public int variableFixe(int variable, Comparateur comparateur, ValeurFixe droite) {
		Gestionnaire gestionnaire = sousGestionnaires.get(variable);

		if (gestionnaire == null)
			return 3;

		return gestionnaire.variableFixe(variable, comparateur, droite);
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
	 * Sous Gestionnaires
	 * ================= */

	/** Définition d'un gestionnaire (sous exécuteur à qui on délègue les tâches) */
	private interface Gestionnaire extends ExtCondition, ExtChangeVariable {
	}

	/**
	 * Comportement lorsqu'une action concernant une variable ignorée est déclenchée
	 */
	private class ComportementIgnore implements Gestionnaire {
		@Override
		public int variableFixe(int variable, Comparateur comparateur, ValeurFixe droite) {
			conditions.push(new ConditionPassThrought<Combat>());
			return 0;
		}

		@Override
		public boolean getBooleenParDefaut() {
			return false;
		}
	}

	/**
	 * Comportement concernant une capacité
	 */
	private class ComportementCapacite implements Gestionnaire {
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
		public int variableFixe(int variable, Comparateur comparateur, ValeurFixe droite) {
			conditions.push(new ConditionOnMembreStat("Capacité", positionMonstre, comparateur, droite.valeur));
			return 0;
		}

		@Override
		public boolean getBooleenParDefaut() {
			return false;
		}
	}

	/**
	 * Comportement concernant l'expérience d'un monstre
	 */
	private class ComportementExperience implements Gestionnaire {
		private int positionMonstre;

		public ComportementExperience(int positionMonstre) {
			this.positionMonstre = positionMonstre;
		}

		public int variableFixe(int variable, Comparateur comparateur, ValeurFixe droite) {
			conditions.push(new ConditionOnMembreStat("EXP", positionMonstre, comparateur, droite.valeur));
			return 0;
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
	private class ComportementGlobalExperience implements Gestionnaire {
		@Override
		public int variableFixe(int variable, Comparateur comparateur, ValeurFixe droite) {
			conditions.push(new ConditionGainExpTotal(comparateur, droite.valeur));
			return 0;
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
			int idMonstre = Utilitaire.getPosition(valeurDroite.idVariable, bdd.contexte.getListeVariables("EXP"));

			if (idMonstre == -1) {
				throw new FinDeCombatException("Modifie un gain d'exp selon une variable qui n'est pas un gain d'exp");
			}

			getElementsFiltres()
					.forEach(c -> c.gainExp = fonctionDApplication.applyAsInt(c.gainExp, expDuMonstre(c, idMonstre)));
		}

		private Integer expDuMonstre(Combat combat, int position) {
			Monstre monstre = combat.getMonstre(position);
			return monstre == null ? 0 : monstre.accessInt(Monstre.STATS).get("EXP");
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
}
