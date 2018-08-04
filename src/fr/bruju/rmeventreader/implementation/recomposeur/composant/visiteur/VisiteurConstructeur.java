package fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur;

import java.util.function.Function;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.Element;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.BoutonConstant;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.BoutonEntree;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.BoutonVariadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.Affectation;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.Conditionnelle;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.Filtre;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.Flip;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.Operation;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionArme;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionBouton;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionFixe;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionValeur;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.ValeurAleatoire;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.ValeurConstante;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.ValeurEntree;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.ValeurVariadique;
import fr.bruju.rmeventreader.utilitaire.lambda.TriFunction;

public class VisiteurConstructeur extends VisiteurRetourneur<Element> {
	@SuppressWarnings("unchecked")
	private <TypeRetour extends Element> Element traiterSerie(TypeRetour elementDeBase, Element[] elementsFils,
			Function<Element[], TypeRetour> reconstruction) {

		Element[] nouveaux = new Element[elementsFils.length];

		boolean creeUnNouveau = false;

		for (int i = 0; i != elementsFils.length; i++) {
			nouveaux[i] = traiter(elementsFils[i]);

			if (nouveaux[i] == null)
				return null;

			if (nouveaux[i] != elementsFils[i]) {
				creeUnNouveau = true;
				nouveaux[i] = nouveaux[i];
			}
		}

		if (!creeUnNouveau)
			return elementDeBase;

		return (TypeRetour) reconstruction.apply(nouveaux).simplifier();
	}

	@Override
	protected Affectation.Valeur traiter(Affectation.Valeur element) {
		return (Affectation.Valeur) traiterSerie(element, new Element[] { element.base },
				(tableau) -> new Affectation.Valeur((Valeur) tableau[0]));
	}

	@Override
	protected Affectation.Bouton traiter(Affectation.Bouton element) {
		return (Affectation.Bouton) traiterSerie(element, new Element[] { element.base },
				(tableau) -> new Affectation.Bouton((Bouton) tableau[0]));
	}

	@Override
	protected Condition traiter(ConditionBouton element) {
		return (Condition) traiterSerie(element, new Element[] { element.interrupteur },
				(tableau) -> new ConditionBouton((Bouton) tableau[0], element.valeur));
	}

	@Override
	protected Condition traiter(ConditionValeur element) {
		return (Condition) traiterSerie(element, new Element[] { element.gauche, element.droite },
				(tableau) -> new ConditionValeur((Valeur) tableau[0], element.operateur, (Valeur) tableau[1]));
	}

	@Override
	protected Operation traiter(Operation element) {
		return (Operation) traiterSerie(element, new Element[] { element.droite },
				(tableau) -> new Operation(element.operateur, (Valeur) tableau[0]));
	}
	

	@Override
	protected Filtre traiter(Filtre element) {
		return (Filtre) traiterSerie(element, new Element[] { element.valeurComparaison, element.valeurFiltrage },
				(tableau) -> new Filtre(element.operateur, (Valeur) tableau[0], (ValeurVariadique) tableau[1]));
	}

	@Override
	protected Conditionnelle.Valeur traiter(Conditionnelle.Valeur element) {
		return traiterConditionnelle(element, element.condition, element.siVrai, element.siFaux,
				(c, v, f) -> new Conditionnelle.Valeur(c, v, f));
	}

	@SuppressWarnings("unchecked")
	private <TypeConditionnelle extends Element, TypeValeur extends Element> TypeConditionnelle traiterConditionnelle(
			TypeConditionnelle element, Condition condition, TypeValeur siVrai, TypeValeur siFaux,
			TriFunction<Condition, TypeValeur, TypeValeur, TypeConditionnelle> reconstruction) {
		Condition c = (Condition) traiter(condition);

		if (c == null)
			return null;

		Boolean ident = ConditionFixe.identifier(c);

		if (ident != null) {
			TypeValeur base = ident ? siVrai : siFaux;
			TypeValeur valeur = (TypeValeur) traiter(base);

			if (valeur == null)
				return null;

			return reconstruction.apply(ConditionFixe.get(true), valeur, null);
		}

		TypeValeur v = (TypeValeur) traiter(siVrai);
		if (v == null)
			return null;

		TypeValeur f = (TypeValeur) traiter(siFaux);
		if (f == null)
			return null;

		if (c == condition && v == siVrai && v == siFaux) {
			return element;
		} else {
			return reconstruction.apply(c, v, f);
		}
	}

	@Override
	protected Conditionnelle.Bouton traiter(Conditionnelle.Bouton element) {
		return traiterConditionnelle(element, element.condition, element.siVrai, element.siFaux,
				(c, v, f) -> new Conditionnelle.Bouton(c, v, f));
	}

	/* ==========
	 * VARIADIQUE
	 * ========== */

	@Override
	protected BoutonVariadique traiter(BoutonVariadique element) {
		return (BoutonVariadique) this.traiterSerie(element, element.getComposants().toArray(new Element[0]),
				tableau -> new BoutonVariadique(tableau));
	}

	@Override
	protected ValeurVariadique traiter(ValeurVariadique element) {
		return (ValeurVariadique) this.traiterSerie(element, element.getComposants().toArray(new Element[0]),
				tableau -> new ValeurVariadique(tableau));
	}

	// Feuilles

	@Override
	protected ConditionArme traiter(ConditionArme element) {
		return element;
	}

	@Override
	protected Flip traiter(Flip element) {
		return element;
	}

	@Override
	protected ValeurConstante traiter(ValeurConstante element) {
		return element;
	}

	@Override
	protected ValeurAleatoire traiter(ValeurAleatoire element) {
		return element;
	}

	@Override
	protected ValeurEntree traiter(ValeurEntree element) {
		return element;
	}

	@Override
	protected ConditionFixe traiter(ConditionFixe element) {
		return element;
	}

	@Override
	protected BoutonConstant traiter(BoutonConstant element) {
		return element;
	}

	@Override
	protected BoutonEntree traiter(BoutonEntree element) {
		return element;
	}
}
