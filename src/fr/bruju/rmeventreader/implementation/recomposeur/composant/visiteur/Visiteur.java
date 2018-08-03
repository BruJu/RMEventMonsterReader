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
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.ValeurAleatoire;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.ValeurConstante;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.ValeurEntree;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.ValeurVariadique;

public interface Visiteur {
	
	public default void visit(Element element) {
		element.accept(this);
	}	
	
	/* ======
	 * NOEUDS
	 * ====== */
	
	public default void visit(BoutonVariadique element) {
		visit(element.composants, BoutonVariadique::new);
	}

	public <U extends CaseMemoire> void visit(Iterable<ComposantVariadique<U>> composants, Supplier<U> recreator);

	public default void visit(ValeurVariadique element) {
		visit(element.composants, ValeurVariadique::new);
	}

	public <T extends CaseMemoire> void visit(Affectation<T> element);

	public <T extends CaseMemoire> void visit(Conditionnelle<T> element);

	public void visit(Operation element);
	
	
	
	/* ========
	 * FEUILLES
	 * ======== */

	public void comportementParDefautFeuille(Element element);
	
	// Boutons
	
	public default void visit(BoutonConstant element) {
		comportementParDefautFeuille(element);
	}

	public default void visit(BoutonEntree element) {
		comportementParDefautFeuille(element);
	}
	
	// Valeurs

	public default void visit(ValeurAleatoire element) {
		comportementParDefautFeuille(element);
	}

	public default void visit(ValeurConstante element) {
		comportementParDefautFeuille(element);
	}

	public default void visit(ValeurEntree element) {
		comportementParDefautFeuille(element);
	}
	
	// Composants variadique
	
	public default void visit(Flip element) {
		comportementParDefautFeuille(element);
	}
	
	
	
	

	
	
	
	
	

	
	
	
}
