package fr.bruju.rmeventreader.implementation.formulatracker.formule.formule;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.Valeur;

public interface FormuleDeDegats {

	String getString();

	Operator getOperator();
	
	
	Valeur getFormule();
	
}
