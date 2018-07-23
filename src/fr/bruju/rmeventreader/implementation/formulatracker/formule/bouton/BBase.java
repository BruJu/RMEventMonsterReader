package fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton;

import fr.bruju.rmeventreader.implementation.formulatracker.simplification.VisiteurDeComposants;
import java.util.Objects;

public class BBase implements Bouton {
	public final int numero; 
	
	public BBase(int numero) {
		this.numero = numero;
	}
	
	@Override
	public String getString() {
		return "S["+numero+"]";
	}

	@Override
	public void accept(VisiteurDeComposants visiteurDeComposant) {
		visiteurDeComposant.visit(this);
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof BBase)) {
			return false;
		}
		BBase castOther = (BBase) other;
		return Objects.equals(numero, castOther.numero);
	}

	@Override
	public int hashCode() {
		return Objects.hash(numero) * 1409;
	}
	
	
	
}
