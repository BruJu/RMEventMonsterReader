package fr.bruju.rmeventreader.implementation.formulatracker.formule.condition;

import fr.bruju.rmeventreader.implementation.formulatracker.simplification.VisiteurDeComposants;
import java.util.Objects;

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

	@Override
	public void accept(VisiteurDeComposants visiteurDeComposant) {
		visiteurDeComposant.visit(this);
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof CArme)) {
			return false;
		}
		CArme castOther = (CArme) other;
		return Objects.equals(heros, castOther.heros) && Objects.equals(objet, castOther.objet)
				&& Objects.equals(haveToOwn, castOther.haveToOwn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(heros, objet, haveToOwn);
	}
	
	
}
