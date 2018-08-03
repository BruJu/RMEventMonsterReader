package fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.CaseMemoire;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.Element;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.Variadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionFixe;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;
import java.util.Objects;

/**
 * Suite de composants variadiques dépendant d'une condition
 * 
 * @author Bruju
 *
 */
public class Conditionnelle<T extends CaseMemoire> implements ComposantVariadique<Variadique<T>> {
	/* =========
	 * COMPOSANT
	 * ========= */

	/** Condition */
	public final Condition condition;
	/** Valeur si la condition est vérifiée */
	public final Variadique<T> siVrai;
	/** Valeur si la condition n'est pas vérifiée */
	public final Variadique<T> siFaux;

	/**
	 * Crée un composant dont la valeur dépend de la condition
	 * @param condition La condition
	 * @param v1 La valeur si la condition est vérifiée
	 * @param v2 La valeur si la condition n'est pas vérifiée
	 */
	public Conditionnelle(Condition condition, Variadique<T> siVrai, Variadique<T> siFaux) {
		this.condition = condition;
		this.siVrai = siVrai;
		this.siFaux = siFaux;
	}
	
	/* ================
	 * AFFICHAGE SIMPLE
	 * ================ */

	@Override
	public String toString() {
		return "[" + condition.toString() + " ? " + siVrai.toString() + " : " + siFaux.toString() + "]";
	}
	
	/* ========
	 * VISITEUR
	 * ======== */
	
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

	/* =================
	 * EQUALS / HASHCODE
	 * ================= */
	
	@Override
	public int hashCode() {
		return Objects.hash(condition, siVrai, siFaux);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object object) {
		if (object instanceof Conditionnelle) {
			Conditionnelle that = (Conditionnelle) object;
			return Objects.equals(this.condition, that.condition) && Objects.equals(this.siVrai, that.siVrai)
					&& Objects.equals(this.siFaux, that.siFaux);
		}
		return false;
	}
}
