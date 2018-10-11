package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.inliner;


import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.Simplification;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;


public class InlinerGlobal implements Simplification {
	
	@Override
	public Algorithme simplifier(Algorithme algorithme) {
		DetecteurDeSimplifications detecteur = new DetecteurDeSimplifications();
		algorithme.acceptInverse(detecteur);
		
		Reecrivain reecrivain = new Reecrivain(algorithme,
				detecteur.instructionsAIgnorer, detecteur.affectationsInlinables);
		return reecrivain.produireResultat();
	}
	



}
