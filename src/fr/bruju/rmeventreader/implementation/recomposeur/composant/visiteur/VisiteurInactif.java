package fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.CaseMemoire;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.Element;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.BoutonConstant;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.BoutonEntree;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.BoutonVariadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.Affectation;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.Conditionnelle;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.Filtre;
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
	default void visit(BoutonConstant element) {
	}

	@Override
	default void visit(BoutonEntree element) {
	}

	@Override
	default void visit(BoutonVariadique element) {
	}

	@Override
	default <T extends CaseMemoire> void visit(Affectation.ABouton element) {
	}

	@Override
	default <T extends CaseMemoire> void visit(Conditionnelle.CBouton element) {
	}

	@Override
	default <T extends CaseMemoire> void visit(Affectation.AValeur element) {
	}

	@Override
	default <T extends CaseMemoire> void visit(Conditionnelle.CValeur element) {
	}

	@Override
	default void visit(Filtre element) {
	}

	@Override
	default void visit(Flip element) {
	}

	@Override
	default void visit(Operation element) {
	}

	@Override
	default void visit(ConditionArme element) {
	}

	@Override
	default void visit(ConditionBouton element) {
	}

	@Override
	default void visit(ConditionFixe element) {
	}

	@Override
	default void visit(ConditionValeur element) {
	}

	@Override
	default void visit(ValeurAleatoire element) {
	}

	@Override
	default void visit(ValeurConstante element) {
	}

	@Override
	default void visit(ValeurEntree element) {
	}

	@Override
	default void visit(ValeurVariadique element) {
	}

	@Override
	default void comportementParDefautFeuille(Element element) {
	}

}
