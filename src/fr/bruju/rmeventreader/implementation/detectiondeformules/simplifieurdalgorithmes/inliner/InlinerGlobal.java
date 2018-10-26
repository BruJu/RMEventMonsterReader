package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.inliner;


import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation.Simplification;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;


public class InlinerGlobal implements Simplification {
	
	@Override
	public Algorithme simplifier(Algorithme algorithme) {
		DetecteurDeSimplifications detecteur = new DetecteurDeSimplifications(new Integer[] {514, 516, 517});
		algorithme.acceptInverse(detecteur);
		
		Reecrivain reecrivain = new Reecrivain(algorithme, detecteur);
		return reecrivain.produireResultat();
	}
}
