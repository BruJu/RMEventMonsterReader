package fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.CaseMemoire;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.Element;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.BoutonConstant;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.BoutonEntree;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.BoutonVariadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.Affectation;
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

public abstract class VisiteurRetourneur<R> implements Visiteur {
	private R elementRetourne;

	public final R traiter(Element element) {
		visit(element);
		return elementRetourne;
	}

	protected abstract <T extends CaseMemoire> R traiter(Affectation<T> element);

	protected abstract R traiter(BoutonConstant element);

	protected abstract R traiter(BoutonEntree element);

	protected abstract R traiter(BoutonVariadique element);

	protected abstract R traiter(ConditionArme element);

	protected abstract R traiter(ConditionBouton element);

	protected abstract R traiter(ConditionFixe element);

	protected abstract <T extends CaseMemoire> R traiter(Conditionnelle<T> element);

	protected abstract R traiter(ConditionValeur element);

	protected abstract R traiter(Flip element);

	protected abstract R traiter(Operation element);

	protected abstract R traiter(ValeurAleatoire element);

	protected abstract R traiter(ValeurConstante element);

	protected abstract R traiter(ValeurEntree element);

	protected abstract R traiter(ValeurVariadique element);
	
	/* ======
	 * VISITE
	 * ====== */

	@Override
	public final <T extends CaseMemoire> void visit(Affectation<T> element) {
		elementRetourne = traiter(element);
	}

	@Override
	public final void visit(BoutonConstant element) {
		elementRetourne = traiter(element);
	}

	@Override
	public final void visit(BoutonEntree element) {
		elementRetourne = traiter(element);
	}

	@Override
	public final void visit(BoutonVariadique element) {
		elementRetourne = traiter(element);
	}

	@Override
	public final void visit(ConditionArme element) {
		elementRetourne = traiter(element);
	}

	@Override
	public final void visit(ConditionBouton element) {
		elementRetourne = traiter(element);
	}

	@Override
	public final void visit(ConditionFixe element) {
		elementRetourne = traiter(element);
	}

	@Override
	public final <T extends CaseMemoire> void visit(Conditionnelle<T> element) {
		elementRetourne = traiter(element);
	}

	@Override
	public final void visit(ConditionValeur element) {
		elementRetourne = traiter(element);
	}

	@Override
	public final void visit(Flip element) {
		elementRetourne = traiter(element);
	}

	@Override
	public final void visit(Operation element) {
		elementRetourne = traiter(element);
	}

	@Override
	public final void visit(ValeurAleatoire element) {
		elementRetourne = traiter(element);
	}

	@Override
	public final void visit(ValeurConstante element) {
		elementRetourne = traiter(element);
	}

	@Override
	public final void visit(ValeurEntree element) {
		elementRetourne = traiter(element);
	}

	@Override
	public final void visit(ValeurVariadique element) {
		elementRetourne = traiter(element);
	}

	@Override
	public void comportementParDefautFeuille(Element element) {
	}
}
