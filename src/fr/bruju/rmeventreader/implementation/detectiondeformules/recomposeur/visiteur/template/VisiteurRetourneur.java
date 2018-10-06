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

public abstract class VisiteurRetourneur<R> implements Visiteur {
	private R elementRetourne;

	public final R traiter(Element element) {
		visit(element);
		return elementRetourne;
	}

	protected R traiter(Affectation element) {
		return null;
	}

	protected R traiter(ConditionArme element) {
		return null;
	}

	protected R traiter(ConditionFixe element) {
		return null;
	}

	protected R traiter(Conditionnelle element) {
		return null;
	}

	protected R traiter(ConditionValeur element) {
		return null;
	}

	protected R traiter(Filtre element) {
		return null;
	}

	protected R traiter(Calcul element) {
		return null;
	}

	protected R traiter(NombreAleatoire element) {
		return null;
	}

	protected R traiter(Constante element) {
		return null;
	}

	protected R traiter(Entree element) {
		return null;
	}

	protected R traiter(Algorithme element) {
		return null;
	}

	protected R traiter(SousAlgorithme element) {
		return null;
	}

	/* ======
	 * VISITE
	 * ====== */

	@Override
	public final void visit(Affectation element) {
		elementRetourne = traiter(element);
	}

	@Override
	public final void visit(Conditionnelle element) {
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
	public final void visit(SousAlgorithme element) {
		elementRetourne = traiter(element);
	}

}
