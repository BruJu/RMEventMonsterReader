package fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton;

import java.util.Objects;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;

/**
 * Etat constant d'un interrupteur, c'est-à-dire soit activé soit désactivé. Les valeurs retournées par cette classe
 * sont toujours les mêmes. C'est-à-dire que l'opérateur == renvoie un résultat correct.
 * 
 * @author Bruju
 *
 */
public class BoutonConstant implements Bouton {
	/* =================
	 * PSEUDO SINGLETON
	 * ================ */
	
	/** Liste des boutons constants connus */
	private static BoutonConstant[] boutons = new BoutonConstant[] { new BoutonConstant(true), new BoutonConstant(false) };
	
	/**
	 * Donne le bouton constant dont l'état est celui fourni
	 * @param value L'état souhaité du bouton constant
	 * @return Le bouton constant ayant l'état demandé
	 */
	public static BoutonConstant get(boolean value) {
		return (value) ? boutons[0] : boutons[1];
	}
	
	/* =========
	 * COMPOSANT
	 * ========= */
	
	/** Valeur de la constante */
	public final boolean valeur;

	/**
	 * Crée une constante avec l'état donné
	 * @param valeur L'état souhaité
	 */
	private BoutonConstant(boolean valeur) {
		this.valeur = valeur;
	}
	
	/**
	 * Inverse le bouton constant
	 */
	public BoutonConstant inverser() {
		return valeur ? boutons[1] : boutons[0];
	}
	
	
	/* ===============
	 * IMPLEMENTATIONS
	 * =============== */

	@Override
	public String toString() {
		return (valeur) ? "ON" : "OFF";
	}
	
	/* ========
	 * VISITEUR
	 * ======== */

	@Override
	public void accept(Visiteur visiteurDeComposant) {
		visiteurDeComposant.visit(this);
	}

	@Override
	public BoutonConstant simplifier() {
		return this;
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
		return Objects.hash("BCONSTANT", valeur);
	}

}
