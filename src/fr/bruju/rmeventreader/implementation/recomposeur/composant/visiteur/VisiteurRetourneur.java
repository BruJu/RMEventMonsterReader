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

public abstract class VisiteurRetourneur<R> implements Visiteur {
	private R elementRetourne;

	public final R traiter(Element element) {
		visit(element);
		return elementRetourne;
	}

	protected abstract R traiter(Affectation element);

	protected abstract R traiter(ConditionArme element);

	protected abstract R traiter(ConditionFixe element);

	protected abstract R traiter(Conditionnelle element);
	
	protected abstract R traiter(ConditionValeur element);

	protected abstract R traiter(Filtre element);
	
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
	public  void visit(Affectation element) {
		elementRetourne = traiter(element);
	}

	@Override
	public  void visit(Conditionnelle element) {
		elementRetourne = traiter(element);
	}


	@Override
	public final void visit(ConditionArme element) {
		elementRetourne = traiter(element);
	}

	@Override
	public final void visit(ConditionFixe element) {
		elementRetourne = traiter(element);
	}

	@Override
	public final void visit(ConditionValeur element) {
		elementRetourne = traiter(element);
	}

	@Override
	public final void visit(Filtre element) {
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
