package fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurDeComposants;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;
import java.util.Objects;

/**
 * Valeur qui est le calcul entre deux valeurs
 * 
 * @author Bruju
 */
public class VCalcul implements Valeur {
	/* =========
	 * COMPOSANT
	 * ========= */
	
	/** Opérande de gauche */
	public final Valeur gauche;
	/** Opérateur */
	public final Operator operateur;
	/** Opérande de droite */
	public final Valeur droite;

	/**
	 * Construit un calcul à partir de deux valeurs et un opérateur
	 * 
	 * @param gauche Valeur de gauche
	 * @param operateur Un opérateur dans +, -, *, /, %
	 * @param droite Valeur de droite
	 */
	public VCalcul(Valeur vGauche, Operator operateur, Valeur vDroite) {
		gauche = vGauche;
		this.operateur = operateur;
		droite = vDroite;
	}

	/* ================
	 * AFFICHAGE SIMPLE
	 * ================ */

	@Override
	public String getString() {
		return getValeurParenthesee(this, gauche) + " " + Utilitaire.getSymbole(operateur) + " "
				+ getValeurParenthesee(this, droite);
	}

	/**
	 * Donne une représentation du fils en considérant qu'il a le père donné pour le parenthésage
	 * @param pere Le père
	 * @param fils Le fils
	 * @return La représentation du fils, entourée de parenthèse si le père est moins prioritaire
	 */
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
	
	/**
	 * Donne la priorité de l'opération de ce calcul
	 * @return La priorité de l'opérateur de ce calcul
	 */
	private int getPriorite() {
		return Utilitaire.getPriorite(operateur);
	}
	
	/* ========
	 * VISITEUR
	 * ======== */

	@Override
	public void accept(VisiteurDeComposants visiteurDeComposant) {
		visiteurDeComposant.visit(this);
	}

	/* =================
	 * EQUALS / HASHCODE
	 * ================= */

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
