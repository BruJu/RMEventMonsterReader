package fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.visiteur.template;


import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.Element;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.condition.ConditionArme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.condition.ConditionFixe;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.condition.ConditionValeur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.operation.Affectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.operation.Calcul;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.operation.Conditionnelle;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.operation.Filtre;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.operation.SousAlgorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.valeur.Constante;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.valeur.Entree;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.valeur.NombreAleatoire;

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

	/**
	 * Visite de composant
	 */
	public void visit(SousAlgorithme element);

	/* =========
	 * CONDITION
	 * ========= */

	/**
	 * Visite de composant
	 */
	public void visit(ConditionArme element);

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
	public void visit(NombreAleatoire element);

	/**
	 * Visite de composant
	 */
	public void visit(Constante element);

	/**
	 * Visite de composant
	 */
	public void visit(Entree element);

	/**
	 * Visite de composant
	 */
	public void visit(Algorithme element);
}
