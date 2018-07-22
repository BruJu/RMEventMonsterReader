package fr.bruju.rmeventreader.implementation.formulatracker.formule.condition;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton.Bouton;

public class CSwitch implements Condition {
	private Bouton interrupteur;
	private boolean valeur;

	public CSwitch(Bouton interrupteur, boolean valeur) {
		this.interrupteur = interrupteur;
		this.valeur = valeur;
	}

	@Override
	public Condition revert() {
		return new CSwitch(interrupteur, !valeur);
	}

	@Override
	public String getString() {
		String s = "";
		
		if (!valeur)
			s += "¬";
		
		s += interrupteur.getString();
		
		return s;
	}

}
