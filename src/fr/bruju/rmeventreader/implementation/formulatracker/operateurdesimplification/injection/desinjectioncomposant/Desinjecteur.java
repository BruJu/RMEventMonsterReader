package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.injection.desinjectioncomposant;

import java.util.Collection;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.ConstructeurDeComposantR;

public class Desinjecteur extends ConstructeurDeComposantR {
	
	// TODO : utiliser des gestionnaires de condition afin de filtrer correctement les conditions fausses
	private Collection<CVariable> conditionsAEnlever;
	private Collection<CVariable> conditionsAEnleverInverse;

	public Composant desinjecter(Composant composant, Collection<CVariable> conditionsAEnlever) {
		if (conditionsAEnlever == null) {
			return composant;
		}
		
		this.conditionsAEnlever = conditionsAEnlever;
		remplirInverse();
		return traiter(composant);
	}

	private void remplirInverse() {
		conditionsAEnleverInverse = conditionsAEnlever
				.stream()
				.map(c -> (CVariable) c.revert())
				.collect(Collectors.toList());
	}

	@Override
	protected Composant traiter(CVariable cVariable) {
		if (conditionsAEnlever.contains(cVariable)) {
			return CFixe.get(true);
		}

		if (conditionsAEnleverInverse.contains(cVariable)) {
			return CFixe.get(false);
		}
		
		return super.traiter(cVariable);
	}
}
