package fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.condition;

import java.util.Objects;

import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.Element;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.ElementFeuille;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.ElementIntermediaire;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.visiteur.template.Visiteur;

/**
 * Condition toujours vraie ou toujours fausse
 * 
 * @author Bruju
 *
 */
public final class ConditionFixe implements Condition, ElementFeuille, ElementIntermediaire {
	/* =================
	 * PSEUDO SINGLETON
	 * ================ */

	/** Liste des conditiosn fixes */
	private static ConditionFixe[] conditionsFixes = new ConditionFixe[] { new ConditionFixe(true),
			new ConditionFixe(false) };

	/**
	 * Donne le bouton constant dont l'état est celui fourni
	 * 
	 * @param value L'état souhaité du bouton constant
	 * @return Le bouton constant ayant l'état demandé
	 */
	public static ConditionFixe get(boolean value) {
		return (value) ? conditionsFixes[0] : conditionsFixes[1];
	}

	/**
	 * Renvoie vrai ou faux si la condition donnée est une condition fixe. Renvoie null sinon.
	 */
	public static Boolean identifier(Condition c) {
		if (c == conditionsFixes[0]) {
			return Boolean.TRUE;
		} else if (c == conditionsFixes[1]) {
			return Boolean.FALSE;
		} else {
			return null;
		}
	}

	/* =========
	 * COMPOSANT
	 * ========= */

	/** Valeur de la constante */
	public final boolean value;

	/**
	 * Crée une constante avec l'état donné
	 * 
	 * @param value L'état souhaité
	 */
	private ConditionFixe(boolean value) {
		this.value = value;
	}

	@Override
	public Condition revert() {
		return get(!value);
	}

	@Override
	public ConditionFixe simplifier() {
		return this;
	}

	/* ================
	 * AFFICHAGE SIMPLE
	 * ================ */

	@Override
	public String toString() {
		return (value) ? "CVRAI" : "CFAUX";
	}

	/* ========
	 * VISITEUR
	 * ======== */

	@Override
	public void accept(Visiteur visiteurDeComposants) {
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
		return Objects.hash("CFIXE", value);
	}

	@Override
	public Element[] getFils() {
		return new Element[0];
	}

	@Override
	public ElementIntermediaire fonctionDeRecreation(Element[] elementsFils) {
		return this;
	}
}
