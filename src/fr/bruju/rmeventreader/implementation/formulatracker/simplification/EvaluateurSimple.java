package fr.bruju.rmeventreader.implementation.formulatracker.simplification;

import java.util.Stack;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;

public class EvaluateurSimple implements VisiteurDeComposantsADefaut {
	private Stack<Integer> pile = new Stack<>();
	
	public Integer evaluer(Valeur valeur) {
		visit(valeur);
		return pile.pop();
	}
	
	@Override
	public void visit(VCalcul vCalcul) {
		visit(vCalcul.gauche);
		visit(vCalcul.droite);
		
		Integer droite = pile.pop();
		Integer gauche = pile.pop();
		
		if (gauche == null || droite == null) {
			pile.push(null);
		} else {
			pile.push(vCalcul.operateur.compute(gauche, droite));
		}
	}

	@Override
	public void visit(VConstante composant) {
		pile.push(composant.valeur);
	}
	
	@Override
	public void comportementParDefaut() {
		pile.push(null);
	}
}
