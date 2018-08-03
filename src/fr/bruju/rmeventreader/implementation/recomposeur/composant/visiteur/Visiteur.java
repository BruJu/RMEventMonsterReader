package fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur;

import java.util.function.Supplier;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.CaseMemoire;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.Element;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.Variadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.BoutonConstant;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.BoutonEntree;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.BoutonVariadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.Affectation;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.ComposantVariadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.Conditionnelle;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.Flip;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.Operation;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionArme;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionBouton;
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
	
	/* ======
	 * BOUTON
	 * ====== */

	/**
	 * Visite de composant
	 */
	public default void visit(BoutonConstant element) {
		comportementParDefautFeuille(element);
	}

	/**
	 * Visite de composant
	 */
	public default void visit(BoutonEntree element) {
		comportementParDefautFeuille(element);
	}

	/**
	 * Visite de composant
	 */
	public void visit(BoutonVariadique element);

	
	/* ====================
	 * COMPOSANT VARIADIQUE
	 * ==================== */

	/**
	 * Visite de composant
	 */
	public <T extends CaseMemoire> void visit(Affectation<T> element);

	/**
	 * Visite de composant
	 */
	public <T extends CaseMemoire> void visit(Conditionnelle<T> element);

	/**
	 * Visite de composant
	 */
	public default void visit(Flip element) {
		comportementParDefautFeuille(element);
	}

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
	public void visit(ConditionBouton element);

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
	 * Visite de composants
	 */
	public <U extends Variadique<?>> void visit(Iterable<ComposantVariadique<U>> composants, Supplier<U> recreator);

	/**
	 * Visite d'une feuille sans traitement spécifique
	 */
	public void comportementParDefautFeuille(Element element);
}
