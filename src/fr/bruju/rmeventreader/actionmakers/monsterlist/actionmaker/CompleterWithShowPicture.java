package fr.bruju.rmeventreader.actionmakers.monsterlist.actionmaker;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.actionner.ReturnValue;
import fr.bruju.rmeventreader.actionmakers.monsterlist.manipulation.ConditionOnMonsterId;
import fr.bruju.rmeventreader.actionmakers.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.actionmakers.monsterlist.metier.Monstre;

public class CompleterWithShowPicture extends StackedActionMaker<Monstre> {
	// Constantes
	private static final int SHOW_PIC_ID_WITH_NAME = 19;
	private static final int VARIABLE_IDMONSTRE = 559;
	private static final int VARIABLE_IDCOMBAT  = 435;
	
	// Sous classe : Condition
	
	@Override
	protected List<Monstre> getAllElements() {
		return database.extractMonsters();
	}
	
	// Monstres
	
	private MonsterDatabase database;
	
	public CompleterWithShowPicture(MonsterDatabase database) {
		this.database = database;
	}
	
	
	
	// Interprétation des instructions

	
	@Override
	public boolean caresAboutCondOnVariable(int leftOperandValue, Operator operatorValue, ReturnValue returnValue) {
		return (leftOperandValue == VARIABLE_IDMONSTRE || leftOperandValue == VARIABLE_IDCOMBAT)
				&& returnValue.type == ReturnValue.Type.VALUE;
	}
	
	
	@Override
	public void showPicture(int id, String pictureName) {
		if (id != SHOW_PIC_ID_WITH_NAME) {
			return;
		}
		
		List<Monstre> monstres = this.getElementsFiltres();
		
		
		for (Monstre monstre : monstres) {
			monstre.name = pictureName;
		}
	}

	@Override
	public void condOnVariable(int leftOperandValue, Operator operatorValue, ReturnValue returnValue) {
		conditions.push(new ConditionOnMonsterId(leftOperandValue == VARIABLE_IDMONSTRE, operatorValue, returnValue.value));
	}


}
