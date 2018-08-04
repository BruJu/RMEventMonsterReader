package fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionFixe;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.ValeurVariadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;

import java.util.List;
import java.util.Objects;

/**
 * Suite de composants variadiques dépendant d'une Cond()
 * 
 * @author Bruju
 *
 */
public class Conditionnelle implements ComposantVariadique {
	

	public final Condition condition;
	public final ValeurVariadique siVrai;
	public final ValeurVariadique siFaux;

	public Conditionnelle(Condition condition, ValeurVariadique siVrai, ValeurVariadique siFaux) {
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

	@Override
	public boolean cumuler(List<ComposantVariadique> nouveauxComposants) {
		Boolean identifie = ConditionFixe.identifier(condition);

		if (identifie == null) {
			nouveauxComposants.add(this);
			return true;
		}
		
		nouveauxComposants.addAll(identifie ? siVrai.composants : siFaux.composants);

		return false;
	}

	
	/* ========
	 * VISITEUR
	 * ======== */

	@Override
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}

	@Override
	public ComposantVariadique simplifier() {
		Condition cSimplifiee = condition;

		Boolean identifie = ConditionFixe.identifier(cSimplifiee);

		if (identifie != null) {
			if (identifie) {
				return new Conditionnelle(ConditionFixe.get(true), siVrai, null);
			} else {
				return new Conditionnelle(ConditionFixe.get(true), siFaux, null);
			}
		} else {
			return this;
		}
	}


	/* =================
	 * EQUALS / HASHCODE
	 * ================= */
	@Override
	public int hashCode() {
		return Objects.hash(condition, siVrai, siFaux);
	}


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
