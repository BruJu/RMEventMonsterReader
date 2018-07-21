package fr.bruju.rmeventreader.implementation.formulatracker.actionmaker;

import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.donnees.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VAleatoire;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VBase;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.Valeur;

public class Traducteur {
	private Map<Integer, Valeur> variablesConnues;
	
	// Singleton
	public Traducteur(Map<Integer, Valeur> variables) {
		this.variablesConnues = variables;
	}
	
	// Traduction

	public Valeur getValue(ValeurFixe returnValue) {
		return new VConstante(returnValue.get());
	}

	public Valeur getValue(Variable returnValue) {
		Integer idVariable = returnValue.get();
		
		Valeur v = variablesConnues.get(idVariable);
		
		if (v == null) {
			v = new VBase(idVariable);
			variablesConnues.put(idVariable, v);
		}
		
		return v;
	}

	public Valeur getValue(ValeurAleatoire returnValue) {
		return new VAleatoire(returnValue.getMin(), returnValue.getMax());
	}


}
