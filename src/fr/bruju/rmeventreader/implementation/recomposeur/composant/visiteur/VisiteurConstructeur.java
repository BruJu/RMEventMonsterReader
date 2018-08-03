package fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.Element;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.Variadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.BoutonConstant;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.BoutonEntree;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.BoutonVariadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.Affectation;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.ComposantVariadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.Conditionnelle;
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

public class VisiteurConstructeur extends VisiteurRetourneur<Element> {
	

	@Override
	protected BoutonVariadique traiter(BoutonVariadique element) {
		return traiterVariadique(element.getComposants(), element, BoutonVariadique::new);
	}
	
	@Override
	protected ValeurVariadique traiter(ValeurVariadique element) {
		return traiterVariadique(element.getComposants(), element, ValeurVariadique::new);
	}

	@Override
	protected Element traiter(Affectation.Valeur element) {
		Valeur base = element.base;
		Valeur traitee = (Valeur) traiter(base);
		
		if (traitee == null)
			return null;
		
		if (base == traitee)
			return element;
		
		return new Affectation.Valeur(traitee);
	}


	@Override
	protected Element traiter(Affectation.Bouton element) {
		Bouton base = element.base;
		Bouton traitee = (Bouton) traiter(base);
		
		if (traitee == null)
			return null;
		
		if (base == traitee)
			return element;
		
		return new Affectation.Bouton(traitee);
	}
	
	@Override
	protected Element traiter(Conditionnelle.Valeur element) {
		Condition c = (Condition) traiter(element.condition);
		
		if (c == null)
			return null;
		
		Boolean ident = ConditionFixe.identifier(c);
		
		if (ident != null) {
			return new Conditionnelle.Valeur(ConditionFixe.get(true),
					 (ident ? traiter(element.siVrai) : traiter(element.siFaux)),
					 null);
		}
		
		ValeurVariadique v = traiter(element.siVrai);
		ValeurVariadique f = traiter(element.siFaux);
		
		if (c == element.condition && v == element.siVrai && v == element.siFaux) {
			return element;
		} else {
			return new Conditionnelle.Valeur(c, v, f);
		}
	}


	@Override
	protected Element traiter(Conditionnelle.Bouton element) {
		Condition c = (Condition) traiter(element.condition);
		
		if (c == null)
			return null;
		
		Boolean ident = ConditionFixe.identifier(c);
		
		if (ident != null) {
			return new Conditionnelle.Bouton(ConditionFixe.get(true),
					 (ident ? traiter(element.siVrai) : traiter(element.siFaux)),
					 null);
		}
		
		BoutonVariadique v = traiter(element.siVrai);
		BoutonVariadique f = traiter(element.siFaux);
		
		if (c == element.condition && v == element.siVrai && v == element.siFaux) {
			return element;
		} else {
			return new Conditionnelle.Bouton(c, v, f);
		}
	}

	@Override
	protected Element traiter(ConditionBouton element) {
		Bouton t = (Bouton) traiter(element.interrupteur);
		
		if (t == null)
			return null;
		
		if (t == element.interrupteur)
			return element;
		
		return new ConditionBouton(t, element.valeur);
	}

	@Override
	protected Element traiter(ConditionValeur element) {
		Valeur g = (Valeur) traiter(element.gauche);
		Valeur d = (Valeur) traiter(element.droite);
		
		if (g == null || d == null)
			return null;
		
		if (g == element.gauche && d == element.droite)
			return element;
		
		return new ConditionValeur(g, element.operateur, d);
	}

	@Override
	protected Element traiter(Operation element) {
		Valeur t = (Valeur) element.droite;
		
		if (t == null)
			return null;
		
		if (t == element.droite)
			return element;
		
		return new Operation(element.operateur, t);
	}



	@SuppressWarnings("unchecked")
	private <T extends Variadique<?>> T traiterVariadique(List<? extends Element> elementsOrigine, T base,
			Function<List<ComposantVariadique<T>>, T> reconstitution) {
		boolean modification = false;
		
		List<ComposantVariadique<T>> sousElements = new ArrayList<>(elementsOrigine.size());
		
		Element resultat;
		
		for (Element a : elementsOrigine) {
			resultat = traiter(a);
			
			if (resultat == null) {
				return null;
			}
			
			sousElements.add((ComposantVariadique<T>) resultat);
		}
		
		if (!modification) {
			return base;
		} else {
			return reconstitution.apply(sousElements);
		}
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
