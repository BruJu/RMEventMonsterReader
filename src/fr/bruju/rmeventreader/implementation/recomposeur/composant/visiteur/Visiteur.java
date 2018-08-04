package fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur;


import fr.bruju.rmeventreader.implementation.recomposeur.composant.Element;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.Affectation;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.Conditionnelle;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.Filtre;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.Calcul;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionArme;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionFixe;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionValeur;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.NombreAleatoire;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Constante;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Entree;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Algorithme;

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
	public void visit(Calcul element);
	

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
	public default void visit(NombreAleatoire element) {
		comportementParDefautFeuille(element);
	}

	/**
	 * Visite de composant
	 */
	public default void visit(Constante element) {
		comportementParDefautFeuille(element);
	}

	/**
	 * Visite de composant
	 */
	public default void visit(Entree element) {
		comportementParDefautFeuille(element);
	}

	/**
	 * Visite de composant
	 */
	public void visit(Algorithme element); 

	
	/* ======
	 * OUTILS
	 * ====== */
	
	/**
	 * Visite d'une feuille sans traitement spécifique
	 */
	public void comportementParDefautFeuille(Element element);
}
