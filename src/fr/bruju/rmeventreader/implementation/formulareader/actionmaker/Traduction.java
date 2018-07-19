package fr.bruju.rmeventreader.implementation.formulareader.actionmaker;

import fr.bruju.rmeventreader.actionmakers.donnees.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NewValeur;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.simple.ValeurNumerique;

public class Traduction {
	public static ValeurNumerique getValue(ValeurFixe value) {
		return NewValeur.Numerique(value.get());
	}

	public static ValeurNumerique getValue(ValeurAleatoire value) {
		return NewValeur.Numerique(value.getMin(), value.getMax());
	}

	public static Valeur getValue(Etat etat, Variable value) {
		int idVariable = value.get();
		
		return etat.getValeur(idVariable);
	}

}
