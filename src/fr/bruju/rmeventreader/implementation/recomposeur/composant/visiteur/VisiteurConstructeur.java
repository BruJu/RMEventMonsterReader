package fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur;


import fr.bruju.rmeventreader.implementation.recomposeur.composant.Element;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.ElementIntermediaire;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.Affectation;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.Conditionnelle;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.Filtre;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.Calcul;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionArme;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionFixe;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionValeur;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.NombreAleatoire;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Constante;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Entree;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Algorithme;

public class VisiteurConstructeur extends VisiteurRetourneur<Element> {

	/* ======================
	 * ELEMENTS AYANT UN PERE
	 * ====================== */
	
	@SuppressWarnings("unchecked")
	protected <T extends ElementIntermediaire> T traiterIntermediaire(T element) {
		Element[] elementsFils = element.getFils();
		
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
			return element;

		return (T) element.fonctionDeRecreation(nouveaux).simplifier();
	}

	
	@Override
	protected Affectation traiter(Affectation element) {
		return traiterIntermediaire(element);
	}

	@Override
	protected Condition traiter(ConditionValeur element) {
		return traiterIntermediaire(element);
	}

	@Override
	protected Calcul traiter(Calcul element) {
		return traiterIntermediaire(element);
	}

	@Override
	protected Filtre traiter(Filtre element) {
		return traiterIntermediaire(element);
	}

	@Override
	protected Algorithme traiter(Algorithme element) {
		return traiterIntermediaire(element);
	}

	/* ==========================
	 * OPERATIONS CONDITIONNELLES
	 * ========================== */
	
	@Override
	protected Conditionnelle traiter(Conditionnelle element) {
		Condition condition = element.condition;
		Algorithme siVrai = element.siVrai;
		Algorithme siFaux = element.siFaux;
		
		Condition c = (Condition) traiter(condition);

		if (c == null)
			return null;

		Boolean ident = ConditionFixe.identifier(c);

		if (ident != null) {
			Algorithme base = ident ? siVrai : siFaux;
			Algorithme valeur = (Algorithme) traiter(base);

			if (valeur == null)
				return null;

			return new Conditionnelle(ConditionFixe.get(true), valeur, null);
		}

		Algorithme v = (Algorithme) traiter(siVrai);
		if (v == null)
			return null;

		Algorithme f = (Algorithme) traiter(siFaux);
		if (f == null)
			return null;

		if (c == condition && v == siVrai && v == siFaux) {
			return element;
		} else {
			return new Conditionnelle(c, v, f);
		}
	}

	/* ========
	 * FEUILLES
	 * ======== */

	@Override
	protected ConditionArme traiter(ConditionArme element) {
		return element;
	}

	@Override
	protected Constante traiter(Constante element) {
		return element;
	}

	@Override
	protected NombreAleatoire traiter(NombreAleatoire element) {
		return element;
	}

	@Override
	protected Entree traiter(Entree element) {
		return element;
	}

	@Override
	protected ConditionFixe traiter(ConditionFixe element) {
		return element;
	}
}
