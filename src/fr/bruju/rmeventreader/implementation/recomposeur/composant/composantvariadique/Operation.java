package fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.Variadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

import java.util.List;
import java.util.Objects;

/**
 * Effectue une opération arithmétique avec une autre valeur
 * 
 * @author Bruju
 */
public class Operation implements ComposantVariadique<Variadique<Valeur>> {
	/* =========
	 * COMPOSANT
	 * ========= */

	/** Opérateur */
	public final Operator operateur;
	/** Opérande de droite */
	public final Valeur droite;

	/**
	 * Construit un calcul à partir de deux valeurs et un opérateur
	 * 
	 * @param operateur Un opérateur dans -, *, /, %
	 * @param droite Valeur de droite
	 */
	public Operation(Operator operateur, Valeur droite) {
		this.operateur = operateur;
		this.droite = droite;
	}
	
	/* ================
	 * IMPLEMENTATIONS
	 * ================ */

	@Override
	public String toString() {
		return Utilitaire.getSymbole(operateur) + " " + droite.toString();
	}
	
	/* ========
	 * VISITEUR
	 * ======== */

	@Override
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}

	@Override
	public Operation simplifier() {
		return this;
	}

	/* =================
	 * EQUALS / HASHCODE
	 * ================= */
	
	@Override
	public int hashCode() {
		return Objects.hash(operateur, droite);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Operation) {
			Operation that = (Operation) object;
			return Objects.equals(this.operateur, that.operateur) && Objects.equals(this.droite, that.droite);
		}
		return false;
	}

	@Override
	public boolean cumuler(List<ComposantVariadique<? extends Variadique<Valeur>>> nouveauxComposants) {
		return false;
	}
}
