package fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression;

import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.visiteurs.VisiteurDExpression;

import java.util.Objects;

public class Constante implements Expression {
	public final int valeur;

	@Override
	public String getString() {
		return Integer.toString(valeur);
	}

	@Override
	public String getStringAvecPriorite(int prioriteActuelle) {
		return Integer.toString(valeur);
	}

	public Constante(int valeur) {
		this.valeur = valeur;
	}

	@Override
	public void accept(VisiteurDExpression visiteurDExpression) {
		visiteurDExpression.visit(this);
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(valeur);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Constante) {
			Constante that = (Constante) object;
			return this.valeur == that.valeur;
		}
		return false;
	}

	@Override
	public Integer evaluer() {
		return valeur;
	}
}
