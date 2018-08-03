package fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur;

import java.util.ArrayList;
import java.util.stream.Stream;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.Element;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.BoutonConstant;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.BoutonEntree;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.BoutonVariadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.Flip;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.ValeurAleatoire;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.ValeurConstante;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.ValeurEntree;

public interface Visiteur {
	
	public default void visit(Element element) {
		element.accept(this);
	}

	
	/* ======
	 * NOEUDS
	 * ====== */

	public default void visit(Iterable<? extends Element> elements) {
		for (Element element : elements) {
			visit(element);
		}
	}
	
	public default void visit(BoutonVariadique element) {
		visit(element.composants);
	}
	
	
	
	/* ========
	 * FEUILLES
	 * ======== */

	public default void comportementParDefautFeuille(Element element) {
		// Aucun comportement
	}
	
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
