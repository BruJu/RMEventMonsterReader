package fr.bruju.rmeventreader.actionmakers.executeur.handlerInstructions;

import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.calcul.OpMathematique;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurGauche;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.EvenementDeplacable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.NombreObjet;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Pointeur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurDeplacable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurDivers;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Variable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.VariableHeros;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.VariablePlage;

public class ModificationDeVariables implements Remplisseur {

	@Override
	public void remplirMap(Map<Integer, HandlerInstruction> handlers) {
		// Modification d'interrupteurs
		handlers.put(10210, this::changerSwitch);
		// Modification de variable
		handlers.put(10220, this::changerVariable);
		
	}
	

	private void changerSwitch(ExecuteurInstructions executeur, int[] parametres, String chaine) {
		ValeurGauche valeurGauche = dechiffrerValeurGauche (parametres[0], parametres[1], parametres[2]);
		Boolean nouvelleValeur = dechiffrerBooleen(parametres[3]);
		executeur.Variables_changerSwitch(valeurGauche, nouvelleValeur);
	}
	
	private void changerVariable(ExecuteurInstructions executeur, int[] parametres, String chaine) {
		ValeurGauche valeurGauche = dechiffrerValeurGauche(parametres[0], parametres[1], parametres[2]);
		OpMathematique operateur = extraireOperateurMaths(parametres[3]);
		ValeurDroiteVariable valeurDroite = dechiffrerValeurDroiteVariable(parametres[4], parametres[5], parametres[6]);
		
		if (operateur == null) {
			executeur.Variables_affecterVariable(valeurGauche, valeurDroite);
		} else {
			executeur.Variables_changerVariable(valeurGauche, operateur, valeurDroite);
		}
	}
	
	
	private ValeurDroiteVariable dechiffrerValeurDroiteVariable(int type, int valeur1, int valeur2) {
		switch (type) {
		case 0:
			return new ValeurFixe(valeur1);
		case 1:
			return new Variable(valeur1);
		case 2:
			return new Pointeur(valeur1);
		case 3:
			return new ValeurAleatoire(valeur1, valeur2);
		case 4:
			return new NombreObjet(valeur1, valeur2 == 1);
		case 5:
			return new VariableHeros(valeur2, VariableHeros.Caracteristique.values()[valeur1]);
		case 6:
			return new ValeurDeplacable(new EvenementDeplacable(valeur1),
					ValeurDeplacable.Caracteristique.values()[valeur2]);
		case 7:
			return ValeurDivers.values()[valeur1];
		default:
			throw new ArgumentInconnuException("VDVariable Type  " + type);
		}
	}


	private Boolean dechiffrerBooleen(int action) {
		switch (action) {
		case 0:
			return Boolean.TRUE;
		case 1:
			return Boolean.FALSE;
		case 2:
			return null;
		default:
			throw new ArgumentInconnuException("ChangerSwitch Action " + action);
		}
	}


	private ValeurGauche dechiffrerValeurGauche(int type, int valeur1, int valeur2) {
		switch (type) {
		case 0:
			return new Variable(valeur1);
		case 1:
			return new VariablePlage(valeur1, valeur2);
		case 2:
			return new Pointeur(valeur1);
		default:
			throw new ArgumentInconnuException("ChangerSwitch Variable type " + type);
		}
	}
	

	
	private OpMathematique extraireOperateurMaths(int numero) {
		switch (numero) {
		case 0:
			return null;
		case 1:
			return OpMathematique.PLUS;
		case 2:
			return OpMathematique.MOINS;
		case 3:
			return OpMathematique.FOIS;
		case 4:
			return OpMathematique.DIVISE;
		case 5:
			return OpMathematique.MODULO;
		default:
			throw new ArgumentInconnuException("Extraire Operateur Methmatique " + numero);
		}
	}
}
