package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.transformations.inliner;


import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.ExprVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.nouvellestransformations.RemplaceAlgorithme;

import java.util.List;
import java.util.function.Function;


public class InlinerGlobal extends RemplaceAlgorithme {
	private final Function<Algorithme, List<ExprVariable>> determinateurDeSorties;

	public InlinerGlobal(Function<Algorithme, List<ExprVariable>> determinateurDeSorties) {
		this.determinateurDeSorties = determinateurDeSorties;
	}



	@Override
	public Algorithme simplifier(Algorithme algorithme) {
		List<ExprVariable> variablesVivantes = determinateurDeSorties.apply(algorithme);

		DetecteurDeSimplifications detecteur = new DetecteurDeSimplifications(variablesVivantes);
		algorithme.acceptInverse(detecteur);
		
		Reecrivain reecrivain = new Reecrivain(algorithme, detecteur);
		return reecrivain.produireResultat();
	}

	public static List<ExprVariable> lireLesVariablesVivantes(Algorithme algorithme) {
		ListeurDeVariablesDeSorties listeur = new ListeurDeVariablesDeSorties();
		listeur.visit(algorithme);
		return listeur.get();
	}
}
