package fr.bruju.rmeventreader.implementation.monsterlist.actionmaker;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMakerWithConditionalInterest;
import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.ReturnValue;
import fr.bruju.rmeventreader.actionmakers.donnees.rework.Variable;
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
public class DropCompleter implements ActionMakerWithConditionalInterest {
	private static final int VARIABLE_ID_MONSTRE = 552;
	private static final int VARIABLE_ID_DROP = 2120;
	
	private int dernierIfLu = -1;
	
	private MonsterDatabase db;
	
	public DropCompleter(MonsterDatabase db) {
		this.db = db;
	}
	

	@Override
	public boolean caresAboutCondOnVariable(int idVariable, Operator operatorValue, ReturnValue returnValue) {
		if (idVariable != VARIABLE_ID_MONSTRE)
			return false;
		
		if (operatorValue != Operator.IDENTIQUE || returnValue.type != ReturnValue.Type.VALUE)
			throw new DropCompleterException("Comparaison entre la variable ID_MONSTRE et quelque chose de différent que identique à une valeur fixe");
		
		return true;
	}
	
	@Override
	public void changeVariable(Variable variable, Operator operator, ReturnValue returnValue) {
		if (variable.get() != VARIABLE_ID_DROP) {
			return;
		}
		
		if (dernierIfLu == -1) {
			throw new DropCompleterException("Affectation hors d'un if donnant l'id du monstre");
		}
		
		db.extractBattles()
		  .stream()
		  .flatMap(combat -> combat.getMonstersStream())
		  .filter(monstre -> monstre != null && monstre.getId() == dernierIfLu)
		  .forEach(monstre -> monstre.nomDrop = Integer.toString(returnValue.value));
	}


	@Override
	public void condOnVariable(int leftOperandValue, Operator operatorValue, ReturnValue returnValue) {
		dernierIfLu = returnValue.value;
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