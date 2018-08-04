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

/**
 * Interface de visiteur qui ne fait jamais rien
 * 
 * @author Bruju
 *
 */
public interface VisiteurInactif extends Visiteur {

	@Override
	default void visit(Element element) {
	}

	@Override
	default void visit(Affectation element) {
	}

	@Override
	default void visit(Conditionnelle element) {
	}

	@Override
	default void visit(Filtre element) {
	}
	
	@Override
	default void visit(Calcul element) {
	}

	@Override
	default void visit(ConditionArme element) {
	}

	@Override
	default void visit(ConditionFixe element) {
	}

	@Override
	default void visit(ConditionValeur element) {
	}

	@Override
	default void visit(NombreAleatoire element) {
	}

	@Override
	default void visit(Constante element) {
	}

	@Override
	default void visit(Entree element) {
	}

	@Override
	default void visit(Algorithme element) {
	}

	@Override
	default void comportementParDefautFeuille(Element element) {
	}

}
