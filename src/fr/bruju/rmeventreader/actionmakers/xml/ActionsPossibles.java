package fr.bruju.rmeventreader.actionmakers.xml;

import java.util.HashMap;
import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;
import fr.bruju.rmeventreader.actionmakers.donnees.LeftValue;
import fr.bruju.rmeventreader.actionmakers.donnees.Pointeur;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.actionmakers.donnees.VariablePlage;

public class ActionsPossibles {
	public static interface Action {
		public void exec(ActionMaker actionMaker, String string, int[] parameters);
	}
	
	public static Map<Long, Action> remplirActions() {
		return new ActionsPossibles().getMap();
	}
	
	private Map<Long, Action> getMap() {
		Map<Long, Action> actions = new HashMap<>();

		actions.put(10L, this::vide);
		actions.put(10110L, this::showMessage);
		actions.put(10120L, this::messageOptions);
		actions.put(10130L, this::messagePortrait);
		actions.put(10140L, this::qcm);
		actions.put(10150L, this::saisieDeNombre);
		actions.put(20110L, this::showMessageSuite);
		actions.put(10210L, this::changeSwitch);
		actions.put(20140L, this::qcmChoix);
		actions.put(20141L, this::qcmFin);

		
		
		
		return actions;
	}

	private void vide(ActionMaker actionMaker, String string, int[] parameters) {
	}
	

	private void changeSwitch(ActionMaker actionMaker, String string, int[] parameters) {
		int type = parameters[0]; // 0 = unique, 1 = plage, 2 = pointeurs 
		int debut = parameters[1];
		int fin = parameters[2];
		int action = parameters[3]; // 0 on, 1 off, 2 reverse
		
		LeftValue v;
		
		if (type == 0) {
			v = new Variable(debut);
		} else if (type == 1) {
			v = new VariablePlage(debut, fin);
		} else {
			v = new Pointeur(debut);
		}
		
		if (action == 2) {
			actionMaker._revertSwitch(v);
		} else {
			actionMaker._changeSwitch(v, action == 0);
		}
	}
	
	private void messageOptions(ActionMaker actionMaker, String string, int[] parameters) {
		boolean transparent = parameters[0] == 1;
		int position = parameters[1]; // Haut = 0, Milieu = 1, Bas = 2
		boolean positionnementAuto = parameters[2] == 1;
		boolean bloquant = parameters[3] == 1;
		
		actionMaker.notImplementedFeature("Message Options " + transparent + position + positionnementAuto + bloquant);
	}
	
	private void qcm(ActionMaker actionMaker, String string, int[] parameters) {
		actionMaker.notImplementedFeature("QCM");
	}

	private void qcmFin(ActionMaker actionMaker, String string, int[] parameters) {
		actionMaker.notImplementedFeature("QCMFIN");
	}
	
	private void qcmChoix(ActionMaker actionMaker, String string, int[] parameters) {
		if (string.equals("")) {
			actionMaker.notImplementedFeature("QCM Choix : defaut");
		} else {
			actionMaker.notImplementedFeature("QCM Choix : " + string);
		}
		
	}

	
	private void messagePortrait(ActionMaker actionMaker, String string, int[] parameters) {
		if (string.equals("")) {
			actionMaker.notImplementedFeature("EffacerPortrait");
		} else {
			String faceset = string;
			int numeroImage = parameters[0];
			boolean inverse = parameters[1] == 1;
			boolean aDroite = parameters[1] == 1;
			actionMaker.notImplementedFeature("Portrait " + faceset + " " + numeroImage + " "
					+ inverse + " " + aDroite);
		}
	}

	private void showMessage(ActionMaker actionMaker, String string, int[] parameters) {
		actionMaker.notImplementedFeature("Show Message : " + string);
	}

	private void showMessageSuite(ActionMaker actionMaker, String string, int[] parameters) {
		actionMaker.notImplementedFeature("Show Message | " + string);
	}
	
	private void saisieDeNombre(ActionMaker actionMaker, String string, int[] parameters) {
		int nbDeChiffres = parameters[0];
		int variables = parameters[1];
		
		actionMaker.notImplementedFeature("SaisieNombre : " + nbDeChiffres + " " + variables);
	}
	
}
