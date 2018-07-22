package fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton;

public class BConstant implements Bouton {
	private static Bouton[] boutons = new Bouton[] {new BConstant(true), new BConstant(false)};
	
	private boolean value;
	
	private BConstant(boolean value) {
		this.value = value;
	}

	public static Bouton get(boolean value) {
		return (value) ? boutons[0] : boutons[1];
	}

	@Override
	public String getString() {
		return (value) ? "ON" : "OFF";
	}
	
	
}
