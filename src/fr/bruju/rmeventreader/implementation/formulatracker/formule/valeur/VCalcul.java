package fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

public class VCalcul implements Valeur {
	private Valeur gauche;
	private Operator operateur;
	private Valeur droite;

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

}
