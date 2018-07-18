package fr.bruju.rmeventreader.implementation.monsterlist.actionmaker;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMakerDefalse;
import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;


/**
 *
 * 
 * Instructions d'intéret :
 * <> Fork Condition: If Variable [552] == 0 then ...
 * <> Change Variable: [2120] = 0
 * <>
 * : End of fork
 *
 */
public class DropCompleter implements ActionMakerDefalse {
	private static final int VARIABLE_ID_MONSTRE = 552;
	private static final int VARIABLE_ID_DROP = 2120;
	
	private int dernierIfLu = -1;
	
	private MonsterDatabase db;
	
	public DropCompleter(MonsterDatabase db) {
		this.db = db;
	}
	

	@Override
	public boolean condOnVariable(int leftOperandValue, Operator operatorValue, ValeurFixe returnValue) {
		if (returnValue.get() != VARIABLE_ID_MONSTRE)
			return false;
		
		if (operatorValue != Operator.IDENTIQUE) {
			dernierIfLu = returnValue.get();
			return true;
		} else {
			throw new DropCompleterException("Comparaison entre la variable ID_MONSTRE et quelque chose de différent que identique à une valeur fixe");
		}
	}
	
	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurFixe returnValue) {
		if (variable.get() != VARIABLE_ID_DROP) {
			return;
		}
		
		if (dernierIfLu == -1) {
			throw new DropCompleterException("Affectation hors d'un if donnant l'id du monstre");
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

	
	/**
	 * Exceptions jetées par le Drop Completer
	 */
	public static class DropCompleterException extends RuntimeException {
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