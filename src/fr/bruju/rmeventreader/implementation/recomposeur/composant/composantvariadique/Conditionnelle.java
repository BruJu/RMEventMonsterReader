package fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.CaseMemoire;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.Element;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.Variadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionFixe;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;

public class Conditionnelle<T extends CaseMemoire> implements ComposantVariadique<Variadique<T>> {
	public final Condition condition;
	public final Variadique<T> siVrai;
	public final Variadique<T> siFaux;

	public Conditionnelle(Condition condition, Variadique<T> siVrai, Variadique<T> siFaux) {
		this.condition = condition;
		this.siVrai = siVrai;
		this.siFaux = siFaux;
	}

	@Override
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}

	@Override
	public Element simplifier() {
		Condition cSimplifiee = condition.simplifier();
		
		Boolean identifie = ConditionFixe.identifier(cSimplifiee);
		
		if (identifie != null) {
			return identifie ? siVrai.simplifier() : siFaux.simplifier();
		}
		
		Variadique<T> vraiSimplifie = siVrai.simplifier();
		Variadique<T> fauxSimplifie = siFaux.simplifier();
		
		if (cSimplifiee == condition && vraiSimplifie == siVrai && fauxSimplifie == siFaux) {
			return this;
		} else {
			return new Conditionnelle<T>(cSimplifiee, vraiSimplifie, fauxSimplifie);
		}
	}
}
