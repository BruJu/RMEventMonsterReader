package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.injection.desinjectioncomposant;

import java.util.Collection;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.ConstructeurDeComposantR;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.gestionnairedecondition.CreateurDeGestionnaire;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.gestionnairedecondition.GestionnaireDeCondition;

public class Desinjecteur extends ConstructeurDeComposantR {
	
	// TODO : utiliser des gestionnaires de condition afin de filtrer correctement les conditions fausses
	private Collection<GestionnaireDeCondition> gestionnaires;
	
	public Composant desinjecter(Composant composant, Collection<? extends Condition> conditionsAEnlever) {
		if (conditionsAEnlever == null) {
			return composant;
		}
		
		remplirConditions(conditionsAEnlever);
		return traiter(composant);
	}

	private void remplirConditions(Collection<? extends Condition> conditionsAEnlever) {
		CreateurDeGestionnaire createur = new CreateurDeGestionnaire();
		
		gestionnaires = conditionsAEnlever.stream()
						.map(c -> createur.getGestionnaire(c))
						.collect(Collectors.toList());
	}

	@Override
	protected Composant traiter(CVariable cVariable) {
		CVariable c = cVariable;
		Condition r;
		
		for (GestionnaireDeCondition gestionnaire : gestionnaires) {
			r = gestionnaire.conditionVariable(c);
			
			Boolean ident = CFixe.identifier(r);
			
			if (ident != null) {
				return r;
			}
			c = (CVariable) r;
		}
		return c;
	}
}
