package fr.bruju.rmeventreader.implementation.recomposeur;

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
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.NombreAleatoire;
import fr.bruju.rmeventreader.actionmakers.composition.visiteur.template.VisiteurConstructeur;

public class aaaa extends VisiteurConstructeur {

	@Override
	protected Condition modifier(ConditionArme element) {
		return ConditionFixe.get(true);
	}

	@Override
	protected Condition modifier(ConditionValeur element) {
		return ConditionFixe.get(true);
	}

	@Override
	protected Algorithme modifier(Algorithme element) {
		
		for (Element op : element.getFils()) {
			if (op instanceof Conditionnelle) {
				throw new RuntimeException("J4EN AI MARE" + element.toString());
			}
		}
		
		return super.modifier(element);
	}




	@Override
	protected Affectation modifier(Affectation element) {
		for (Element op : element.getFils()) {
			if (op instanceof Conditionnelle) {
				throw new RuntimeException("J4EN AI MARE" + element.toString());
			}
		}
		return super.modifier(element);
	}

	@Override
	protected Calcul modifier(Calcul element) {
		for (Element op : element.getFils()) {
			if (op instanceof Conditionnelle) {
				throw new RuntimeException("J4EN AI MARE" + element.toString());
			}
		}
		return super.modifier(element);
	}

	@Override
	protected Filtre modifier(Filtre element) {
		for (Element op : element.getFils()) {
			if (op instanceof Conditionnelle) {
				throw new RuntimeException("J4EN AI MARE" + element.toString());
			}
		}
		return super.modifier(element);
	}

	@Override
	protected ElementIntermediaire modifier(ElementIntermediaire element) {
		for (Element op : element.getFils()) {
			if (op instanceof Conditionnelle) {
				throw new RuntimeException("J4EN AI MARE" + element.toString());
			}
		}
		return super.modifier(element);
	}

	
}
