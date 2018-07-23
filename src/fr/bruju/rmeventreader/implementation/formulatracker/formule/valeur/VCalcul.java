package fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.VisiteurDeComposants;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;
import java.util.Objects;

public class VCalcul implements Valeur {
	public final Valeur gauche;
	public final Operator operateur;
	public final Valeur droite;

	public VCalcul(Valeur vGauche, Operator operateur, Valeur vDroite) {
		gauche = vGauche;
		this.operateur = operateur;
		droite = vDroite;
	}

	@Override
	public String getString() {
		return getValeurParenthesee(this, gauche) + " " + Utilitaire.getSymbole(operateur) + " "
				+ getValeurParenthesee(this, droite);
	}

	private static String getValeurParenthesee(VCalcul pere, Valeur fils) {
		if (fils instanceof VCalcul) {
			VCalcul sousCalcul = (VCalcul) fils;

			if (sousCalcul.getPriorite() < pere.getPriorite()) {
				return "(" + fils.getString() + ")";
			} else {
				return fils.getString();
			}
		} else {
			return fils.getString();
		}
	}

	private int getPriorite() {
		return Utilitaire.getPriorite(operateur);
	}
	

	@Override
	public void accept(VisiteurDeComposants visiteurDeComposant) {
		visiteurDeComposant.visit(this);
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof VCalcul)) {
			return false;
		}
		VCalcul castOther = (VCalcul) other;
		return Objects.equals(gauche, castOther.gauche) && Objects.equals(operateur, castOther.operateur)
				&& Objects.equals(droite, castOther.droite);
	}

	@Override
	public int hashCode() {
		return Objects.hash(gauche, operateur, droite);
	}

	
	
}

