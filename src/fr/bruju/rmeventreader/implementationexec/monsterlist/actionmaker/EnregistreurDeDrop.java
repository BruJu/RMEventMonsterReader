package fr.bruju.rmeventreader.implementationexec.monsterlist.actionmaker;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExtChangeVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExtCondition;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ModuleExecFlot;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ModuleExecVariables;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Comparateur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Variable;
import fr.bruju.rmeventreader.implementationexec.monsterlist.metier.MonsterDatabase;


/**
 * Cette classe permet de remplir une base de données au niveau des drop à partir d'un script contenant des instructions
 * simples.
 * 
 * Instructions d'intéret :
 * <pre>
 * <> Fork Condition: If Variable [552] == 0 then ...
 * <> Change Variable: [2120] = 0
 * : End of fork
 * </pre>
 */
public class EnregistreurDeDrop implements ExecuteurInstructions, ExtCondition.$, ExtChangeVariable.$ {
	/* ============
	 * Construction
	 * ============ */
	/** Variable contenant l'id du monstre */
	private final int VARIABLE_ID_MONSTRE;
	/** Variable contenant l'id de l'objet */
	private final int VARIABLE_ID_DROP;
	
	/** Dernier if lu */
	private int dernierIfLu = -1;
	
	/** Base de données à remplir */
	private MonsterDatabase db;
	
	/**
	 * Instancie un enregistreur de drops
	 * @param db La base de données à compléter
	 */
	public EnregistreurDeDrop(MonsterDatabase db) {
		this.db = db;

		this.VARIABLE_ID_MONSTRE = db.contexte.getVariable("EnregistreurDrop_MonstreID");
		this.VARIABLE_ID_DROP = db.contexte.getVariable("EnregistreurDrop_CombatID");
	}

	/* =======
	 * Modules
	 * ======= */

	@Override
	public ModuleExecVariables getExecVariables() {
		return this;
	}
	
	@Override
	public ModuleExecFlot getExecFlot() {
		return this;
	}
	
	/* =========
	 * Variables
	 * ========= */

	@Override
	public void affecterVariable(Variable variable, ValeurFixe fixe) {
		if (variable.idVariable != VARIABLE_ID_DROP) {
			return;
		}
		
		if (dernierIfLu == -1) {
			throw new DropCompleterException(DropCompleterException.MessageChangement);
		}
		
		db.extractMonsters()
		  .stream()
		  .filter(monstre -> monstre.getId() == dernierIfLu)
		  .forEach(monstre -> monstre.nomDrop = Integer.toString(fixe.valeur));
	}

	/* =========
	 * Condition
	 * ========= */
	
	public boolean variableFixe(int variable, Comparateur comparateur, ValeurFixe droite) {
		if (variable != VARIABLE_ID_MONSTRE)
			return false;
		
		if (comparateur != Comparateur.IDENTIQUE) {
			throw new DropCompleterException(DropCompleterException.MessageCondition);
		}

		dernierIfLu = droite.valeur;
		
		return true;
	}
	
	@Override
	public void Flot_siNon() {
		throw new DropCompleterException("Condition Else");
	}
	
	@Override
	public void Flot_siFin() {
		dernierIfLu = -1; 
	}
	
	
	/* =========
	 * Exception
	 * ========= */
	
	/**
	 * Exceptions jetées par le Drop Completer
	 */
	public static class DropCompleterException extends RuntimeException {
		/**
		 * Message signalant l'affectation d'un drop sans connaître l'id du monstre
		 */
		public static final String MessageChangement = "Affectation hors d'un if donnant l'id du monstre";
		
		/**
		 * Message signalant que la condition n'est pas identique
		 */
		public static final String MessageCondition = 
			"Comparaison entre la variable ID_MONSTRE et quelque chose de différent que identique à une valeur fixe";
		
		/**
		 * Serial ID unique
		 */
		private static final long serialVersionUID = -5435094486561966642L;
		
		/**
		 * Crée une exception avec le message donné
		 * @param message Le message décrivant l'erreur
		 */
		public DropCompleterException(String message) {
			super("DropCompleter :" + message);
		}
	}
	

	
}