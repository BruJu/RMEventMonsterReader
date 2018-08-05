package fr.bruju.rmeventreader.actionmakers.composition.visiteur.template;


import fr.bruju.rmeventreader.actionmakers.composition.composant.Element;
import fr.bruju.rmeventreader.actionmakers.composition.composant.ElementIntermediaire;
import fr.bruju.rmeventreader.actionmakers.composition.composant.condition.Condition;
import fr.bruju.rmeventreader.actionmakers.composition.composant.condition.ConditionArme;
import fr.bruju.rmeventreader.actionmakers.composition.composant.condition.ConditionFixe;
import fr.bruju.rmeventreader.actionmakers.composition.composant.condition.ConditionValeur;
import fr.bruju.rmeventreader.actionmakers.composition.composant.operation.Affectation;
import fr.bruju.rmeventreader.actionmakers.composition.composant.operation.Calcul;
import fr.bruju.rmeventreader.actionmakers.composition.composant.operation.Conditionnelle;
import fr.bruju.rmeventreader.actionmakers.composition.composant.operation.Filtre;
import fr.bruju.rmeventreader.actionmakers.composition.composant.operation.Operation;
import fr.bruju.rmeventreader.actionmakers.composition.composant.operation.SousAlgorithme;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Constante;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Entree;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.NombreAleatoire;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Valeur;

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
	private <T extends R, R extends ElementIntermediaire> R traiterIntermediaire(T element) {
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

		if (creeUnNouveau)
			element = (T) element.fonctionDeRecreation(nouveaux);
		
		R retour = (R) modifier(element);
		
		if (retour == null)
			return null;
		
		return (R) retour.simplifier();
	}


	@Override
	protected final Affectation traiter(Affectation element) {
		return traiterIntermediaire(element);
	}

	@Override
	protected final Condition traiter(ConditionValeur element) {
		return traiterIntermediaire(element);
	}

	@Override
	protected final Calcul traiter(Calcul element) {
		return traiterIntermediaire(element);
	}

	@Override
	protected final Filtre traiter(Filtre element) {
		return traiterIntermediaire(element);
	}

	@Override
	protected final Algorithme traiter(Algorithme element) {
		return traiterIntermediaire(element);
	}

	@Override
	protected final SousAlgorithme traiter(SousAlgorithme element) {
		return traiterIntermediaire(element);
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

		if (c == null)		return null;
		
		Boolean ident = ConditionFixe.identifier(c);

		if (ident != null) {
			return traiter(new SousAlgorithme(ident ? siVrai : siFaux));
		}
		
		// Traitement vrai
		avantDeTraiter(c);
		Algorithme v = (Algorithme) traiter(siVrai);
		finDeTraitement();
		if (v == null)		return null;
		
		// Traitement faux
		avantDeTraiter(c.revert());
		Algorithme f = (Algorithme) traiter(siFaux);
		finDeTraitement();
		if (f == null)		return null;

		// Tout identique
		if (c == condition && v == siVrai && v == siFaux) {
			return element;
		} else {
			// On a la certitude que ce n'est pas un sous algorithme car on a déjà testé l'identification plus haut
			return modifier((Conditionnelle) new Conditionnelle(c, v, f).simplifier());
		}
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
		return element;
	}

}
