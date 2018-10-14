package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression;

import fr.bruju.rmdechiffreur.modele.ValeurAleatoire;
import java.util.Objects;

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

	@Override
	public int hashCode() {
		return Objects.hash(valeurMin, valeurMax);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof NombreAleatoire) {
			NombreAleatoire that = (NombreAleatoire) object;
			return this.valeurMin == that.valeurMin && this.valeurMax == that.valeurMax;
		}
		return false;
	}
	
}
