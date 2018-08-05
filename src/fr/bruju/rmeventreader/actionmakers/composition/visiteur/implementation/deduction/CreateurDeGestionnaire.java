package fr.bruju.rmeventreader.actionmakers.composition.visiteur.implementation.deduction;

import fr.bruju.rmeventreader.actionmakers.composition.composant.condition.Condition;
import fr.bruju.rmeventreader.actionmakers.composition.composant.condition.ConditionArme;
import fr.bruju.rmeventreader.actionmakers.composition.composant.condition.ConditionFixe;
import fr.bruju.rmeventreader.actionmakers.composition.composant.condition.ConditionValeur;
import fr.bruju.rmeventreader.actionmakers.composition.composant.operation.Affectation;
import fr.bruju.rmeventreader.actionmakers.composition.composant.operation.Calcul;
import fr.bruju.rmeventreader.actionmakers.composition.composant.operation.Conditionnelle;
import fr.bruju.rmeventreader.actionmakers.composition.composant.operation.Filtre;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Constante;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Entree;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.NombreAleatoire;
import fr.bruju.rmeventreader.actionmakers.composition.visiteur.template.VisiteurRetourneur;

/**
 * Il s'agit d'une clase permettant de créer des gestionnaires de conditions
 * 
 * @author Bruju
 * 
 */
public class CreateurDeGestionnaire extends VisiteurRetourneur<GestionnaireDeCondition> {
	/**
	 * Donne le gestionnaire de conditions poru la condition donnée
	 */
	public GestionnaireDeCondition getGestionnaire(Condition condition) {
		return traiter(condition);
	}
	
	@Override
	protected GestionnaireDeCondition traiter(ConditionArme cArme) {
		return new GestionnaireArme(cArme);
	}
	
	@Override
	protected GestionnaireDeCondition traiter(ConditionValeur cVariable) {
		Integer valeur = Constante.evaluer(cVariable.droite);
		
		if (valeur == null) {
			return null;
		}

		switch (cVariable.operateur) {
		case IDENTIQUE:
			return new GestionnaireVariableIdentique(cVariable, valeur);
		case DIFFERENT:
			return new GestionnaireVariableDifferent(cVariable, valeur);
		case INF:
		case INFEGAL:
		case SUP:
		case SUPEGAL:
			return new GestionnaireVariableInegal(cVariable);
		default:
			return null;
		}
	}

	
	/* ======================
	 * Traitements par défaut
	 * ====================== */
	
	@Override
	protected GestionnaireDeCondition traiter(Affectation element) {
		return null;
	}

	@Override
	protected GestionnaireDeCondition traiter(ConditionFixe element) {
		return null;
	}

	@Override
	protected GestionnaireDeCondition traiter(Conditionnelle element) {
		return null;
	}

	@Override
	protected GestionnaireDeCondition traiter(Filtre element) {
		return null;
	}

	@Override
	protected GestionnaireDeCondition traiter(Calcul element) {
		return null;
	}

	@Override
	protected GestionnaireDeCondition traiter(NombreAleatoire element) {
		return null;
	}

	@Override
	protected GestionnaireDeCondition traiter(Constante element) {
		return null;
	}

	@Override
	protected GestionnaireDeCondition traiter(Entree element) {
		return null;
	}

	@Override
	protected GestionnaireDeCondition traiter(Algorithme element) {
		return null;
	}
}
