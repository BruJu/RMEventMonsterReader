package fr.bruju.rmeventreader.actionmakers.xml;

import java.util.HashMap;
import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;
import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.LeftValue;
import fr.bruju.rmeventreader.actionmakers.donnees.Pointeur;
import fr.bruju.rmeventreader.actionmakers.donnees.RightValue;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.actionmakers.donnees.VariablePlage;

public class ActionsPossibles {
	public static interface Action {
		public void exec(ActionMaker actionMaker, String string, int[] parameters);
	}

	public static Map<Long, Action> remplirActions() {
		return new ActionsPossibles().getMap();
	}

	private void vide(ActionMaker actionMaker, String string, int[] parameters) {
	}
	
	private Map<Long, Action> getMap() {
		Map<Long, Action> actions = new HashMap<>();

		actions.put(10L, this::vide);
		actions.put(10110L, this::showMessage);
		actions.put(10120L, this::messageOptions);
		actions.put(10130L, this::messagePortrait);
		actions.put(10140L, this::qcm);
		actions.put(10150L, this::saisieDeNombre);
		actions.put(10210L, this::changeSwitch);
		actions.put(10220L, this::changeVariable);
		actions.put(12330L, this::appelEvenement);
		actions.put(20110L, this::showMessageSuite);
		actions.put(20140L, this::qcmChoix);
		actions.put(20141L, this::qcmFin);

		return actions;

	
	}

	private void appelEvenement(ActionMaker actionMaker, String string, int[] parameters) {
		switch (parameters[0]) {
		case 0:
			actionMaker.callCommonEvent(parameters[1]);
			return;
		case 1:
			if (parameters[1] == 10005) {
				actionMaker.callMapEvent(0, parameters[2]);
			} else {
				actionMaker.callMapEvent(parameters[1], parameters[2]);
			}
			return;
		default:
			afficher(actionMaker, 12330L, string, parameters);
		}
	}
	
	private void changeVariable(ActionMaker actionMaker, String string, int[] parameters) {
		LeftValue left = decrypterLeftValue(parameters);
		Operator operateur = identifierOperateur(parameters[3]);
		RightValue right = decrypterRightValue(parameters[4], parameters[5], parameters[6]); 
		
		if (right != null) {
			actionMaker._changeVariable(left, operateur, right);
		} else {
			actionMaker.notImplementedFeature("ChangeVariable avec comme valeur droite " + parameters[4]);
		}
	}

	private RightValue decrypterRightValue(int type, int nombre1, int nombre2) {
		switch (type) {
		case 0:
			return new ValeurFixe(nombre1);
		case 1:
			return new Variable(nombre1);
		case 2:
			return new Pointeur(nombre1);
		case 3:
			return new ValeurAleatoire(nombre1, nombre2);
		default:
			return null;
		}
	}

	private Operator identifierOperateur(int numero) {
		switch (numero) {
		case 0:
			return Operator.AFFECTATION;
		case 1:
			return Operator.PLUS;
		case 2:
			return Operator.MINUS;
		case 3:
			return Operator.TIMES;
		case 4:
			return Operator.DIVIDE;
		case 5:
			return Operator.MODULO;
		default:
			return null;
		}
	}


	private void changeSwitch(ActionMaker actionMaker, String string, int[] parameters) {
		LeftValue v = decrypterLeftValue(parameters);

		int action = parameters[3]; // 0 on, 1 off, 2 reverse

		if (action == 2) {
			actionMaker._revertSwitch(v);
		} else {
			actionMaker._changeSwitch(v, action == 0);
		}
	}

	private LeftValue decrypterLeftValue(int[] parameters) {
		int type = parameters[0]; // 0 = unique, 1 = plage, 2 = pointeurs 
		int debut = parameters[1];
		int fin = parameters[2];

		if (type == 0) {
			return new Variable(debut);
		} else if (type == 1) {
			return new VariablePlage(debut, fin);
		} else {
			return new Pointeur(debut);
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
			actionMaker
					.notImplementedFeature("Portrait " + faceset + " " + numeroImage + " " + inverse + " " + aDroite);
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

	public static void afficher(ActionMaker actionMaker, long code, String string, int[] parameters) {
		actionMaker.notImplementedFeature("-- CODE NON IMPLEMENTE : " + code + "  -- ");
		if (!string.equals("")) {
			actionMaker.notImplementedFeature("String = " + string);
		}
		
		if (parameters != null) {
			String p = "";
			for (int i : parameters)
				p = p + i + " ";
			actionMaker.notImplementedFeature("Parameters = " + p);
		}
	}
}
