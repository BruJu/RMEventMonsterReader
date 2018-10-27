package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.inliner;


import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.ExprVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation.Simplification;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;

import java.util.List;


public class InlinerGlobal implements Simplification {
	
	@Override
	public Algorithme simplifier(Algorithme algorithme) {

		List<ExprVariable> variablesVivantes = lireLesVariablesVivantes(algorithme);

		DetecteurDeSimplifications detecteur = new DetecteurDeSimplifications(variablesVivantes);
		algorithme.acceptInverse(detecteur);
		
		Reecrivain reecrivain = new Reecrivain(algorithme, detecteur);
		return reecrivain.produireResultat();
	}

	private List<ExprVariable> lireLesVariablesVivantes(Algorithme algorithme) {
		ListeurDeVariablesDeSorties listeur = new ListeurDeVariablesDeSorties();
		listeur.visit(algorithme);
		return listeur.get();
	}
}
