package fr.bruju.rmeventreader.implementation.formulatracker.actionmaker;

import fr.bruju.rmeventreader.actionmakers.donnees.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton.BConstant;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VAleatoire;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.Valeur;

public class Traducteur {
	private FormulaMaker maitre;

	public Traducteur(FormulaMaker maitre) {
		this.maitre = maitre;
	}

	public Valeur getValue(ValeurFixe returnValue) {
		return new VConstante(returnValue.get());
	}

	public Valeur getValue(ValeurAleatoire returnValue) {
		return new VAleatoire(returnValue.getMin(), returnValue.getMax());
	}

	public Valeur getValeurVariable(int numeroVariable) {
		return maitre.getEtat().getVariable(numeroVariable);
	}
	
	public Valeur getValue(Variable returnValue) {
		return getValeurVariable(returnValue.get());
	}
	
	public Bouton getInterrupteur(int numero) {
		return maitre.getEtat().getInterrupteur(numero);
	}
	
	public Bouton getValue(boolean value) {
		return BConstant.get(value);
	}

}
