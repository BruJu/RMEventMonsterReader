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
		actions.put(12010L, this::conditions);
		actions.put(12330L, this::appelEvenement);
		actions.put(20110L, this::showMessageSuite);
		actions.put(20140L, this::qcmChoix);
		actions.put(20141L, this::qcmFin);
		actions.put(11110L, this::afficherImage);

		// Actions très simples
		actions.put(12410L, (a, s, p) -> a.getComment(s));
		actions.put(22010L, (a, s, p) -> a.condElse());
		actions.put(22011L, (a, s, p) -> a.condEnd());

		actions.put(12110L, (a, s, p) -> a.label(p[0]));
		actions.put(12120L, (a, s, p) -> a.jumpToLabel(p[0]));
		

		// Actions non implémentées
		actions.put(10860L, (a, s, p) -> a.notImplementedFeature("• Mod Pos Event"));
		actions.put(11410L, (a, s, p) -> a.notImplementedFeature("• Wait " + ((p[1] == 0) ? p[0] : "Touche")));

		actions.put(12310L, (a, s, p) -> a.notImplementedFeature("• Stopper cet évènement"));
		actions.put(12320L, (a, s, p) -> a.notImplementedFeature("• Effacer cet évènement"));
		actions.put(11340L, (a, s, p) -> a.notImplementedFeature("• Tout déplacer"));
		actions.put(11350L, (a, s, p) -> a.notImplementedFeature("• Tout stopper"));

		actions.put(12210L, (a, s, p) -> a.notImplementedFeature("• Boucle"));
		actions.put(22210L, (a, s, p) -> a.notImplementedFeature("• Fin Boucle"));
		actions.put(12220L, (a, s, p) -> a.notImplementedFeature("• Sortir Boucle"));
		actions.put(11070L, (a, s, p) -> a.notImplementedFeature("• Changement Meteo"));

		actions.put(11510L, (a, s, p) -> a.notImplementedFeature("• Play Music"));
		actions.put(11520L, (a, s, p) -> a.notImplementedFeature("• Effacer Musique en " + p[0] + "ms"));
		actions.put(11530L, (a, s, p) -> a.notImplementedFeature("• Memoriser Musique"));
		actions.put(11540L, (a, s, p) -> a.notImplementedFeature("• Jouer Musique Mémorisée"));
		actions.put(11550L, (a, s, p) -> a
				.notImplementedFeature("• Jouer Son " + s + " Vol/tempo/Bal " + p[0] + "," + p[1] + "," + p[2]));

		actions.put(11720L, (a, s, p) -> a.notImplementedFeature("• Panorama " + s));

		actions.put(11130L, (a, s, p) -> a.notImplementedFeature("• Effacer image " + p[0]));

		
		
		return actions;

	}

	private void afficherImage(ActionMaker actionMaker, String string, int[] parameters) {
		int idImage = parameters[0];
		String nom = string;
		
		actionMaker.showPicture(idImage, nom);
	}
	
	
	
	private void conditions(ActionMaker actionMaker, String string, int[] parameters) {
		if (parameters[0] == 0) {
			// Conditions sur un switch
			boolean valeurSouhaitee = parameters[2] == 0;
			actionMaker.condOnSwitch(parameters[1], valeurSouhaitee);
		} else if (parameters[0] == 1) {
			// Conditions sur une variable
			int numeroVariable = parameters[1];

			Operator operateur = this.identifierOperateurTest(parameters[4]);

			if (parameters[2] == 0) {
				actionMaker.condOnVariable(numeroVariable, operateur, new ValeurFixe(parameters[3]));
			} else {
				actionMaker.condOnVariable(numeroVariable, operateur, new Variable(parameters[3]));
			}
		} else if (parameters[0] == 4) {
			int numeroObjet = parameters[1];

			if (parameters[2] == 0)
				actionMaker.condOnOwnedItem(numeroObjet);
			else
				afficher(actionMaker, -1L, string, parameters);
		} else if (parameters[0] == 5) {
			int numeroHeros = parameters[1];

			if (parameters[2] == 5) {
				actionMaker.condOnEquippedItem(numeroHeros, parameters[3]);
			} else {
				afficher(actionMaker, -1L, string, parameters);
			}
		} else {
			afficher(actionMaker, -1L, string, parameters);
		}
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

	private Operator identifierOperateurTest(int numero) {
		switch (numero) {
		case 0:
			return Operator.IDENTIQUE;
		case 1:
			return Operator.SUPEGAL;
		case 2:
			return Operator.INFEGAL;
		case 3:
			return Operator.SUP;
		case 4:
			return Operator.INF;
		case 5:
			return Operator.DIFFERENT;
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
