package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression;

import fr.bruju.rmdechiffreur.modele.ValeurAleatoire;

public class NombreAleatoire implements Expression {
	public final int valeurMin;
	public final int valeurMax;

	@Override
	public String getString() {
		return Integer.toString(valeurMin) + "~" + Integer.toString(valeurMax);
	}

	public NombreAleatoire(ValeurAleatoire valeur) {
		valeurMin = valeur.valeurMin;
		valeurMax = valeur.valeurMax;
	}

	@Override
	public void accept(VisiteurDExpression visiteurDExpression) {
		visiteurDExpression.visit(this);
	}
	
	
	@Override
	public Integer evaluer() {
		return null;
	}
	
}
