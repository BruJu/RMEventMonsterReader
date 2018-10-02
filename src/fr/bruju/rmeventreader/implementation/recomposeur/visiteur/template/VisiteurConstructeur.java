package fr.bruju.rmeventreader.implementation.recomposeur.visiteur.template;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.Element;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.ElementIntermediaire;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionArme;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionFixe;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionValeur;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.operation.Affectation;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.operation.Calcul;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.operation.Conditionnelle;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.operation.Filtre;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.operation.Operation;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.operation.SousAlgorithme;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Constante;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Entree;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.NombreAleatoire;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Valeur;

public class VisiteurConstructeur extends VisiteurRetourneur<Element> {

	protected void avantDeTraiter(Condition c) {
	}

	protected void finDeTraitement() {
	}

	protected Constante modifier(Constante element) {
		return element;
	}

	protected Condition modifier(ConditionArme element) {
		return element;
	}

	protected NombreAleatoire modifier(NombreAleatoire element) {
		return element;
	}

	protected Valeur modifier(Entree element) {
		return element;
	}

	protected Operation modifier(Conditionnelle conditionnelle) {
		return conditionnelle;
	}

	protected SousAlgorithme modifier(SousAlgorithme conditionnelle) {
		return conditionnelle;
	}

	protected Affectation modifier(Affectation element) {
		return element;
	}

	protected Condition modifier(ConditionValeur element) {
		return element;
	}

	protected Calcul modifier(Calcul element) {
		return element;
	}

	protected Filtre modifier(Filtre element) {
		return element;
	}

	protected Algorithme modifier(Algorithme element) {
		return element;
	}

	protected ElementIntermediaire modifier(ElementIntermediaire element) {
		throw new RuntimeException("Visite d'un élément intermediaire inconnu " + element.getClass());
	}

	/* ======================
	 * ELEMENTS AYANT UN PERE
	 * ====================== */

	@SuppressWarnings("unchecked")
	private <T extends ElementIntermediaire> T traiterIntermediaire(T element) {
		Element[] elementsFils = element.getFils();

		Element[] nouveaux = new Element[elementsFils.length];

		boolean creeUnNouveau = false;

		for (int i = 0; i != elementsFils.length; i++) {
			nouveaux[i] = traiter(elementsFils[i]);

			if (nouveaux[i] == null)
				return null;

			if (nouveaux[i] != elementsFils[i]) {
				creeUnNouveau = true;
			}
		}

		if (creeUnNouveau) {
			element = (T) element.fonctionDeRecreation(nouveaux);
		}

		return element;
	}

	@Override
	protected final Affectation traiter(Affectation element) {
		Affectation retour = traiterIntermediaire(element);
		if (retour == null) return null;
		retour = modifier(retour);
		if (retour == null)
			return null;
		retour = retour.simplifier();
		return retour;
	}

	@Override
	protected final Condition traiter(ConditionValeur element) {
		Condition retour = traiterIntermediaire(element);
		if (retour instanceof ConditionFixe)
			return retour;
		
		if (retour == null) return null;

		retour = modifier((ConditionValeur) retour);
		if (retour == null)
			return null;
		retour = retour.simplifier();
		return retour;
	}

	@Override
	protected final Calcul traiter(Calcul element) {
		Calcul retour = traiterIntermediaire(element);
		if (retour == null) return null;
		retour = modifier(retour);
		if (retour == null)
			return null;
		retour = retour.simplifier();
		return retour;
	}

	@Override
	protected final Filtre traiter(Filtre element) {
		Filtre retour = traiterIntermediaire(element);
		if (retour == null) return null;
		retour = modifier(retour);
		if (retour == null)
			return null;
		retour = retour.simplifier();
		return retour;
	}

	@Override
	protected final Algorithme traiter(Algorithme element) {
		Algorithme retour = traiterIntermediaire(element);
		if (retour == null) return null;
		retour = modifier(retour);
		if (retour == null)
			return null;
		retour = retour.simplifier();
		
		
		
		return retour;
	}

	@Override
	protected final SousAlgorithme traiter(SousAlgorithme element) {
		SousAlgorithme retour = traiterIntermediaire(element);
		if (retour == null) return null;
		retour = modifier(retour);
		if (retour == null)
			return null;
		retour = retour.simplifier();
		return retour;
	}

	/* ==========================
	 * OPERATIONS CONDITIONNELLES
	 * ========================== */

	@Override
	protected final Operation traiter(Conditionnelle element) {
		Condition condition = element.condition;
		Algorithme siVrai = element.siVrai;
		Algorithme siFaux = element.siFaux;

		Condition c = (Condition) traiter(condition);

		if (c == null)
			return null;

		Boolean ident = ConditionFixe.identifier(c);

		if (ident != null) {
			return traiter(new SousAlgorithme(ident ? siVrai : siFaux));
		}

		// Traitement vrai
		avantDeTraiter(c);
		Algorithme v = (Algorithme) traiter(siVrai);
		finDeTraitement();
		if (v == null)
			return null;

		// Traitement faux
		avantDeTraiter(c.revert());
		Algorithme f = (Algorithme) traiter(siFaux);
		finDeTraitement();
		if (f == null)
			return null;

		// Tout identique
		Operation modifiee;

		if (c == condition && v == siVrai && v == siFaux) {
			modifiee = modifier(element);
		} else {
			// On a la certitude que ce n'est pas un sous algorithme car on a déjà testé l'identification plus haut
			modifiee = modifier((Conditionnelle) new Conditionnelle(c, v, f));
		}

		modifiee = (Operation) modifiee.simplifier();

		return modifiee;
	}

	/* ========
	 * FEUILLES
	 * ======== */

	@Override
	protected final Condition traiter(ConditionArme element) {
		return modifier(element);
	}

	@Override
	protected final Constante traiter(Constante element) {
		return modifier(element);
	}

	@Override
	protected final NombreAleatoire traiter(NombreAleatoire element) {
		return modifier(element);
	}

	@Override
	protected final Valeur traiter(Entree element) {
		return modifier(element);
	}

	@Override
	protected final ConditionFixe traiter(ConditionFixe element) {
		throw new RuntimeException("CF visitée");
	}

}
