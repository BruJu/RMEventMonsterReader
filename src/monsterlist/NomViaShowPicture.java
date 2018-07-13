package monsterlist;

import actionner.ActionMaker;
import actionner.Operator;
import actionner.ReturnValue;
import actionner.SwitchChange;
import actionner.SwitchNumber;

public class NomViaShowPicture implements ActionMaker {
	private final static int ID_PICTURE_AVEC_LES_NOMS = 19;
	
	private MonsterDatabase database;
	
	
	public NomViaShowPicture(MonsterDatabase database) {
		this.database = database;
	}

	@Override
	public void showPicture(int id, String pictureName) {
		if (id != ID_PICTURE_AVEC_LES_NOMS)
			return;
		
		
	}
	
	
	@Override
	public void condElse() {
		
	}

	@Override
	public void condEnd() {
		

	}
	

	@Override
	public void condOnVariable(int leftOperandValue, Operator operatorValue, ReturnValue returnValue) {
		
	}

	
	// Non présents

	/**
	 * Exception pour les conditions qui ne sont pas supportées mais qui ont été rencontrées
	 */
	private class NotSupportedConditionException extends RuntimeException {
		
		/**
		 * Id unique
		 */
		private static final long serialVersionUID = -6971624510953898954L;

		/**
		 * Crée une exception pour une condition non supportée par cette classe
		 * @param fonction Le nom de la fonction qui a été appelée
		 */
		public NotSupportedConditionException(String fonction) {
			super("NotSupportedConditionException : cette classe ne supporte pas " + fonction);
		}
	}
	
	@Override
	public void condOnSwitch(int number, boolean value) {
		throw new NotSupportedConditionException("condOnSwitch");

	}

	@Override
	public void condOnEquippedItem(int heroId, int itemId) {
		throw new NotSupportedConditionException("condOnEquippedItem");
	}

	@Override
	public void condOnOwnedItem(int itemId) {
		throw new NotSupportedConditionException("condOnOwnedItem");
	}

	@Override
	public void condTeamMember(int memberId) {
		throw new NotSupportedConditionException("condTeamMember");
	}
	
	
	// Inutilisés

	
	@Override
	public void notImplementedFeature(String str) {
		

	}

	@Override
	public void changeSwitch(SwitchNumber interrupteur, SwitchChange value) {
		

	}

	@Override
	public void changeVariable(SwitchNumber variable, Operator operator, ReturnValue returnValue) {
		

	}

	@Override
	public void modifyItems(ReturnValue idItem, boolean add, ReturnValue quantity) {
		

	}



	@Override
	public void label(int labelNumber) {
		

	}

	@Override
	public void jumpToLabel(int labelNumber) {
		

	}

}
