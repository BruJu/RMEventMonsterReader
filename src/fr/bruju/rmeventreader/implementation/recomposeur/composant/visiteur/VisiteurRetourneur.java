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
	
	protected abstract R traiter(Calcul element);

	protected abstract R traiter(NombreAleatoire element);

	protected abstract R traiter(Constante element);

	protected abstract R traiter(Entree element);

	protected abstract R traiter(Algorithme element);
	
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
	public final void visit(Calcul element) {
		elementRetourne = traiter(element);
	}

	@Override
	public final void visit(NombreAleatoire element) {
		elementRetourne = traiter(element);
	}

	@Override
	public final void visit(Constante element) {
		elementRetourne = traiter(element);
	}

	@Override
	public final void visit(Entree element) {
		elementRetourne = traiter(element);
	}

	@Override
	public final void visit(Algorithme element) {
		elementRetourne = traiter(element);
	}

	@Override
	public void comportementParDefautFeuille(Element element) {
	}
}
