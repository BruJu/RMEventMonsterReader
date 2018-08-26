package fr.bruju.rmeventreader.implementation.random;

import java.util.HashMap;
import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMakerDefalse;
import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.actionmakers.xml.InterpreterMapXMLCache;
import fr.bruju.rmeventreader.utilitaire.PileDeBooleens;

/**
 * Liste les appels à des évènements communs dans les évènements communs
 * 
 * @author Bruju
 *
 */
public class EventChecker implements Runnable {

	@Override
	public void run() {
		for (int i = 1 ; i <= 633 ; i++) {
			afficherEvenement(i);
		}
	}

	/**
	 * Affiche la liste des évènements appelés par l'évènement commun i
	 * @param i Le numéro de l'évènement commun
	 */
	private void afficherEvenement(int i) {
		ECChecker ecChecker = new ECChecker();
		InterpreterMapXMLCache interpreter = new InterpreterMapXMLCache(ecChecker);
		interpreter.inputFile(-1, i, -1);
		
		ecChecker.afficherResultat(i);
	}
	
	public static class ECChecker implements ActionMakerDefalse {
		/** Decompte du nombre d'appel de chaque évènement commun */
		Map<Integer, Integer> map = new HashMap<>();
		/** Une pile de booléens */
		private PileDeBooleens pile = new PileDeBooleens();

		/**
		 * Affiche i suivi du nombre d'utilisations des évènements 277 et 232
		 * @param i Le numéro à afficher au début de la ligne
		 */
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
			if (pile.toutAVrai())
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
