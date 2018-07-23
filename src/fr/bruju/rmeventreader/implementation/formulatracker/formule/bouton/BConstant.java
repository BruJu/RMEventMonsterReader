package fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton;

import fr.bruju.rmeventreader.implementation.formulatracker.simplification.VisiteurDeComposants;
import java.util.Objects;

/**
 * Etat constant d'un interrupteur, c'est-à-dire soit activé soit désactivé. Les valeurs retournées par cette classe
 * sont toujours les mêmes. C'est-à-dire que l'opérateur == renvoie un résultat correct.
 * 
 * @author Bruju
 *
 */
public class BConstant implements Bouton {
	/* =================
	 * PSEUDO SINGLETON
	 * ================ */
	
	/** Liste des boutons constants connus */
	private static BConstant[] boutons = new BConstant[] { new BConstant(true), new BConstant(false) };
	
	/**
	 * Donne le bouton constant dont l'état est celui fourni
	 * @param value L'état souhaité du bouton constant
	 * @return Le bouton constant ayant l'état demandé
	 */
	public static BConstant get(boolean value) {
		return (value) ? boutons[0] : boutons[1];
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
	private BConstant(boolean value) {
		this.value = value;
	}
	
	/* ================
	 * AFFICHAGE SIMPLE
	 * ================ */

	@Override
	public String getString() {
		return (value) ? "ON" : "OFF";
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
		// Seules deux instances uniques sont créées
		return this == other;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value) * 1613;
	}

}
