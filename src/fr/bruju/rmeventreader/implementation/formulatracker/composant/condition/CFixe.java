package fr.bruju.rmeventreader.implementation.formulatracker.composant.condition;

import java.util.Objects;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurDeComposants;

/**
 * Condition toujours vraie ou toujours fausse
 * @author Bruju
 *
 */
public class CFixe implements Condition {
	/* =================
	 * PSEUDO SINGLETON
	 * ================ */
	
	/** Liste des conditiosn fixes */
	private static CFixe[] conditionsFixes = new CFixe[] { new CFixe(true), new CFixe(false) };
	
	/**
	 * Donne le bouton constant dont l'état est celui fourni
	 * @param value L'état souhaité du bouton constant
	 * @return Le bouton constant ayant l'état demandé
	 */
	public static CFixe get(boolean value) {
		return (value) ? conditionsFixes[0] : conditionsFixes[1];
	}
	
	/* =========
	 * COMPOSANT
	 * ========= */
	
	/** Valeur de la constante */
	public final boolean value;

	/**
	 * Crée une constante avec l'état donné
	 * @param value L'état souhaité
	 */
	private CFixe(boolean value) {
		this.value = value;
	}

	@Override
	public Condition revert() {
		return get(!value);
	}
	
	/* ================
	 * AFFICHAGE SIMPLE
	 * ================ */
	
	@Override
	public String getString() {
		return (value) ? "CVRAI" : "CFAUX";
	}

	/* ========
	 * VISITEUR
	 * ======== */

	@Override
	public void accept(VisiteurDeComposants visiteurDeComposants) {
		visiteurDeComposants.visit(this);
	}
	
	/* =================
	 * EQUALS / HASHCODE
	 * ================= */

	@Override
	public boolean equals(final Object other) {
		// Seules deux instances uniques sont créées
		return this == other;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value) * 22279;
	}
}
