package monsterlist.actionmaker.drop;

import actionner.ActionMakerWithConditionalInterest;
import actionner.Operator;
import actionner.ReturnValue;
import actionner.SwitchNumber;
import monsterlist.metier.MonsterDatabase;


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
			throw new RuntimeException("DropCompleter - Comparaison entre la variable ID_MONSTRE et quelque chose de différent que identique à une valeur fixe");
		
		return true;
	}
	
	@Override
	public void changeVariable(SwitchNumber variable, Operator operator, ReturnValue returnValue) {
		if (variable.numberDebut != VARIABLE_ID_DROP) {
			return;
		}
		
		if (dernierIfLu == -1) {
			throw new RuntimeException("DropCompleter - Affectation hors d'un if donnant l'id du monstre");
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
		throw new RuntimeException("DropCompleter - Else");
	}

	@Override
	public void condEnd() {
		dernierIfLu = -1; 
	}

}

/*

*/