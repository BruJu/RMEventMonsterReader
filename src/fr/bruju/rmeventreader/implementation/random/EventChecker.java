package fr.bruju.rmeventreader.implementation.random;

import java.util.HashMap;
import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMakerDefalse;
import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.actionmakers.xml.InterpreterMapXMLCache;
import fr.bruju.rmeventreader.utilitaire.PileDeBooleens;

public class EventChecker implements Runnable {

	@Override
	public void run() {
		for (int i = 1 ; i <= 633 ; i++) {
			afficherEvenement(i);
		}
	}

	private void afficherEvenement(int i) {
		ECChecker ecChecker = new ECChecker();
		InterpreterMapXMLCache interpreter = new InterpreterMapXMLCache(ecChecker);
		interpreter.inputFile(-1, i, -1);
		
		ecChecker.afficherResultat(i);
	}
	
	public static class ECChecker implements ActionMakerDefalse {
		Map<Integer, Integer> map = new HashMap<>();
		PileDeBooleens pile = new PileDeBooleens();

		public void afficherResultat(int i) {
			System.out.println(i + ";" + map.getOrDefault(277, 0) + ";" + map.getOrDefault(232, 0));
		}
		
		
		@Override
		public boolean condOnSwitch(int number, boolean value) {
			pile.empiler(true);
			return true;
		}


		@Override
		public void callCommonEvent(int eventNumber) {
			map.compute(eventNumber, (k, v) -> v == null ? 1 : v + 1);
			
		}

		@Override
		public boolean condOnEquippedItem(int heroId, int itemId) {
			pile.empiler(true);
			return true;
		}

		@Override
		public boolean condOnOwnedSpell(int heroId, int spellId) {
			pile.empiler(true);
			return true;
		}

		@Override
		public boolean condOnVariable(int leftOperandValue, Operator operatorValue, ValeurFixe returnValue) {
			pile.empiler(true);
			return true;
		}

		@Override
		public boolean condOnVariable(int leftOperandValue, Operator operatorValue, Variable returnValue) {
			pile.empiler(true);
			return true;
		}

		@Override
		public boolean condOnOwnedItem(int itemId) {
			if (itemId == 1) {
				pile.empiler(false);
				return false;
			}
			
			return true;
		}

		@Override
		public boolean condTeamMember(int memberId) {
			pile.empiler(true);
			return true;
		}

		@Override
		public void condElse() {
		}

		@Override
		public void condEnd() {
			pile.depiler();
		}
		
		
	}

}
