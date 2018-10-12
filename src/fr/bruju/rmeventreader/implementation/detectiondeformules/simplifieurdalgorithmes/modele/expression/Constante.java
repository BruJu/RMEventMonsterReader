package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression;

import fr.bruju.rmdechiffreur.modele.ValeurFixe;

public class Constante implements Expression {
	public final int valeur;

	@Override
	public String getString() {
		return Integer.toString(valeur);
	}

	public Constante(int valeur) {
		this.valeur = valeur;
	}

	public Constante(ValeurFixe valeurFixe) {
		this.valeur = valeurFixe.valeur;
	}
	

	@Override
	public void accept(VisiteurDExpression visiteurDExpression) {
		visiteurDExpression.visit(this);
	}
	
	@Override
	public Integer evaluer() {
		return valeur;
	}
}
