package fr.bruju.rmeventreader.implementation.monsterlist.actionmaker;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMakerDefalse;
import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;


/**
 * Cette classe permet de remplir une base de données au niveau des drop à partir d'un script contenant des instructions
 * simples.
 * 
 * Instructions d'intéret :
 * <> Fork Condition: If Variable [552] == 0 then ...
 * <> Change Variable: [2120] = 0
 * : End of fork
 *
 */
public class EnregistreurDeDrop implements ActionMakerDefalse {
	/* ============
	 * Construction
	 * ============ */
	
	/**
	 * Variable contenant l'id du monstre
	 */
	private static final int VARIABLE_ID_MONSTRE = 552;
	
	/**
	 * Variable contenant l'id de l'objet
	 */
	private static final int VARIABLE_ID_DROP = 2120;
	
	/**
	 * Dernier if lu
	 */
	private int dernierIfLu = -1;
	
	/**
	 * Base de données à remplir
	 */
	private MonsterDatabase db;
	
	/**
	 * Instancie un enregistreur de drops
	 * @param db La base de données à compléter
	 */
	public EnregistreurDeDrop(MonsterDatabase db) {
		this.db = db;
	}
	
	/* ============
	 * Action Maker
	 * ============ */

	@Override
	public boolean condOnVariable(int leftOperandValue, Operator operatorValue, ValeurFixe returnValue) {
		if (leftOperandValue != VARIABLE_ID_MONSTRE)
			return false;
		
		if (operatorValue == Operator.IDENTIQUE) {
			dernierIfLu = returnValue.get();
			return true;
		} else {
			throw new DropCompleterException(DropCompleterException.MessageCondition);
		}
	}
	
	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurFixe returnValue) {
		if (variable.get() != VARIABLE_ID_DROP) {
			return;
		}
		
		if (dernierIfLu == -1) {
			throw new DropCompleterException(DropCompleterException.MessageChangement);
		}
		
		db.extractMonsters()
		  .stream()
		  .filter(monstre -> monstre.getId() == dernierIfLu)
		  .forEach(monstre -> monstre.setDrop(Integer.toString(returnValue.get())));
	}

	@Override
	public void condElse() {
		throw new DropCompleterException("Condition Else");
	}

	@Override
	public void condEnd() {
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