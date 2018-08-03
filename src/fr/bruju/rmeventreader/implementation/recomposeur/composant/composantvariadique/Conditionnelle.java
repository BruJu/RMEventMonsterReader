package fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.CaseMemoire;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.Element;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionFixe;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;

public class Conditionnelle<T extends CaseMemoire> implements ComposantVariadique<T> {
	public final Condition condition;
	public final T siVrai;
	public final T siFaux;

	public Conditionnelle(Condition condition, T siVrai) {
		this.condition = condition;
		this.siVrai = siVrai;
		this.siFaux = null;
	}
	
	public Conditionnelle(Condition condition, T siVrai, T siFaux) {
		this.condition = condition;
		this.siVrai = siVrai;
		this.siFaux = siFaux;
	}

	@Override
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Element simplifier() {
		Condition cSimplifiee = condition.simplifier();
		
		Boolean identifie = ConditionFixe.identifier(cSimplifiee);
		
		if (identifie != null) {
			return identifie ? siVrai.simplifier() : siFaux.simplifier();
		}
		
		T vraiSimplifie = (T) siVrai.simplifier();
		T fauxSimplifie = (T) siFaux.simplifier();
		
		if (cSimplifiee == condition && vraiSimplifie == siVrai && fauxSimplifie == siFaux) {
			return this;
		} else {
			return new 
		}
	}
}
