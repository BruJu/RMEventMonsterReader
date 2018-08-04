package fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur;


import fr.bruju.rmeventreader.implementation.recomposeur.composant.Element;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.Affectation;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.Conditionnelle;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.Filtre;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.Operation;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionArme;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionFixe;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionValeur;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.ValeurAleatoire;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.ValeurConstante;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.ValeurEntree;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.ValeurVariadique;

public interface Visiteur {

	/**
	 * Visite de composant
	 */
	public default void visit(Element element) {
		element.accept(this);
	}
	
	/* ====================
	 * COMPOSANT VARIADIQUE
	 * ==================== */

	/**
	 * Visite de composant
	 */
	public void visit(Affectation element);

	/**
	 * Visite de composant
	 */
	public void visit(Conditionnelle element);

	/**
	 * Visite de composant
	 */
	public void visit(Filtre element);

	/**
	 * Visite de composant
	 */
	public void visit(Operation element);
	

	/* =========
	 * CONDITION
	 * ========= */

	/**
	 * Visite de composant
	 */
	public default void visit(ConditionArme element) {
		element.accept(this);
	}

	/**
	 * Visite de composant
	 */
	public default void visit(ConditionFixe element) {
		throw new RuntimeException("Condition fixe visitée");
	}

	/**
	 * Visite de composant
	 */
	public void visit(ConditionValeur element);
	
	/* ======
	 * VALEUR
	 * ====== */

	/**
	 * Visite de composant
	 */
	public default void visit(ValeurAleatoire element) {
		comportementParDefautFeuille(element);
	}

	/**
	 * Visite de composant
	 */
	public default void visit(ValeurConstante element) {
		comportementParDefautFeuille(element);
	}

	/**
	 * Visite de composant
	 */
	public default void visit(ValeurEntree element) {
		comportementParDefautFeuille(element);
	}

	/**
	 * Visite de composant
	 */
	public void visit(ValeurVariadique element); 

	
	/* ======
	 * OUTILS
	 * ====== */
	
	/**
	 * Visite d'une feuille sans traitement spécifique
	 */
	public void comportementParDefautFeuille(Element element);
}
