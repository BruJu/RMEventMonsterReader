package fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.Variadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;

public class Operation implements ComposantVariadique<Variadique<Valeur>> {
	public final Operator operateur;
	public final Valeur droite;
	
	public Operation(Operator operateur, Valeur droite) {
		this.operateur = operateur;
		this.droite = droite;
	}

	@Override
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}

	@Override
	public Operation simplifier() {
		Valeur droiteS = droite.simplifier();
		
		if (droiteS == droite) {
			return this;
		} else {
			return new Operation(operateur, droiteS);
		}
	}
}
