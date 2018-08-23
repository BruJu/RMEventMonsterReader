package fr.bruju.rmeventreader.actionmakers.executeur.handlerInstructions;

import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurGauche;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Pointeur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Variable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.VariablePlage;

public class ModificationDeVariables implements Remplisseur {

	@Override
	public void remplirMap(Map<Integer, HandlerInstruction> handlers) {
		// Modification d'interrupteurs
		handlers.put(10210, this::changerSwitch);
		
	}
	

	private void changerSwitch(ExecuteurInstructions executeur, int[] parametres, String chaine) {
		ValeurGauche valeurGauche = dechiffrerValeurGauche (parametres[0], parametres[1], parametres[2]);
		Boolean nouvelleValeur = dechiffrerBooleen(parametres[3]);
		executeur.Variables_changerSwitch(valeurGauche, nouvelleValeur);
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
}
