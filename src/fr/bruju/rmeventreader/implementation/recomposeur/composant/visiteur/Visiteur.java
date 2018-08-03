package fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur;

import java.util.function.Supplier;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.CaseMemoire;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.Element;
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
	
	public default void visit(Element element) {
		element.accept(this);
	}
	
	/* ======
	 * BOUTON
	 * ====== */
	
	public default void visit(BoutonConstant element) {
		comportementParDefautFeuille(element);
	}

	public default void visit(BoutonEntree element) {
		comportementParDefautFeuille(element);
	}

	public default void visit(BoutonVariadique element) {
		visit(element.composants, BoutonVariadique::new);
	}

	
	/* ====================
	 * COMPOSANT VARIADIQUE
	 * ==================== */

	public <T extends CaseMemoire> void visit(Affectation<T> element);

	public <T extends CaseMemoire> void visit(Conditionnelle<T> element);

	public default void visit(Flip element) {
		comportementParDefautFeuille(element);
	}

	public void visit(Operation element);
	

	/* =========
	 * CONDITION
	 * ========= */

	public default void visit(ConditionArme element) {
		element.accept(this);
	}
	
	public void visit(ConditionBouton element);
	
	public default void visit(ConditionFixe element) {
		throw new RuntimeException("Condition fixe visitée");
	}
	
	public void visit(ConditionValeur element);
	
	/* ======
	 * VALEUR
	 * ====== */

	public default void visit(ValeurAleatoire element) {
		comportementParDefautFeuille(element);
	}

	public default void visit(ValeurConstante element) {
		comportementParDefautFeuille(element);
	}

	public default void visit(ValeurEntree element) {
		comportementParDefautFeuille(element);
	}

	public default void visit(ValeurVariadique element) {
		visit(element.composants, ValeurVariadique::new);
	}

	
	/* ======
	 * OUTILS
	 * ====== */
	
	public <U extends CaseMemoire> void visit(Iterable<ComposantVariadique<U>> composants, Supplier<U> recreator);

	public void comportementParDefautFeuille(Element element);
}
