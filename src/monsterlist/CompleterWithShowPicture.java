package monsterlist;

import java.util.ArrayList;
import java.util.List;

import actionner.ActionMakerWithConditionalInterest;
import actionner.Operator;
import actionner.ReturnValue;
import monsterlist.metier.MonsterDatabase;

public class CompleterWithShowPicture implements ActionMakerWithConditionalInterest {
	// Constantes
	private static final int SHOW_PIC_ID_WITH_NAME = 19;
	private static final int VARIABLE_IDMONSTRE = 559;
	private static final int VARIABLE_IDCOMBAT  = 435;
	
	// Sous classe : Condition
	
	public class Condition {
		private boolean onMonstre;
		private Operator operator;
		private int value;
		
		public Condition(boolean onMonster, Operator operator, int value) {
			this.onMonstre = onMonster;
			this.operator = operator;
			this.value = value;
		}
		
		public void revert() {
			operator = operator.revert();
		}
		
		public boolean isRepectedBy(MonsterDatabase.Monstre monstre) {
			if (onMonstre) {
				return operator.test(monstre.getId(), value);
			} else {
				return operator.test(monstre.getBattleId(), value);
			}
		}
	}
	
	private List<Condition> conditions = new ArrayList<>(5);
	
	// Monstres
	
	private List<MonsterDatabase.Monstre> monstres;
	
	public CompleterWithShowPicture(MonsterDatabase database) {
		monstres = database.extractMonsters();
	}
	
	private List<MonsterDatabase.Monstre> filterMonstres() {
		List<MonsterDatabase.Monstre> monstresFiltres = new ArrayList<>();
		
		explorationMonstre:
		for (MonsterDatabase.Monstre monstre : monstres) {
			for (Condition condition : conditions) {
				if (!condition.isRepectedBy(monstre)) {
					continue explorationMonstre;
				}
			}
			
			monstresFiltres.add(monstre);
		}
		
		return monstresFiltres;
	}
	
	
	
	// Interprétation des instructions

	
	@Override
	public boolean caresAboutCondOnVariable(int leftOperandValue, Operator operatorValue, ReturnValue returnValue) {
		return leftOperandValue == VARIABLE_IDMONSTRE || leftOperandValue == VARIABLE_IDCOMBAT;
	}
	
	
	@Override
	public void showPicture(int id, String pictureName) {
		if (id != SHOW_PIC_ID_WITH_NAME) {
			return;
		}
		
		List<MonsterDatabase.Monstre> monstres = filterMonstres();
		
		
		for (MonsterDatabase.Monstre monstre : monstres) {
			monstre.name = pictureName;
		}
	}

	@Override
	public void condOnVariable(int leftOperandValue, Operator operatorValue, ReturnValue returnValue) {
		if (returnValue.type != ReturnValue.Type.VALUE) {
			throw new RuntimeException("rip");
		}
		
		conditions.add(new Condition(leftOperandValue == VARIABLE_IDMONSTRE, operatorValue, returnValue.value));
	}

	@Override
	public void condElse() {
		conditions.get(conditions.size() - 1).revert();
	}

	@Override
	public void condEnd() {
		conditions.remove(conditions.size() - 1);
	}

}
