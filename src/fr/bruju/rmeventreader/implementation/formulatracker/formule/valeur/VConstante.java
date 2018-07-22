package fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur;

public class VConstante implements Valeur {
	public final int valeur;
	
	public VConstante(int valeur) {
		this.valeur = valeur;
	}

	@Override
	public String getString() {
		return Integer.toString(valeur);
	}

}
