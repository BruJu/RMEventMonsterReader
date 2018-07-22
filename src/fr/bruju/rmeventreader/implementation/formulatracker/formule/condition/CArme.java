package fr.bruju.rmeventreader.implementation.formulatracker.formule.condition;

public class CArme implements Condition {
	public final int heros;
	public final int objet;
	public final boolean haveToOwn;
	
	public CArme(int heros, int objet) {
		this.heros = heros;
		this.haveToOwn = true;
		this.objet = objet;
	}
	
	private CArme(int heros, int objet, boolean haveToOwn) {
		this.heros = heros;
		this.haveToOwn = haveToOwn;
		this.objet = objet;
	}

	@Override
	public Condition revert() {
		return new CArme(heros, objet, !haveToOwn);
	}

	@Override
	public String getString() {
		return "Heros #" + heros + " " + ( haveToOwn ? "∋" : "∌") + " Objet " + objet;
	}

}
