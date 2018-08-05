package fr.bruju.rmeventreader.actionmakers.composition.composant.operation;

import fr.bruju.rmeventreader.actionmakers.composition.composant.condition.Condition;
import fr.bruju.rmeventreader.actionmakers.composition.composant.condition.ConditionFixe;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.actionmakers.composition.visiteur.template.Visiteur;

import java.util.List;
import java.util.Objects;

/**
 * Suite de composants variadiques dépendant d'une Cond()
 * 
 * @author Bruju
 *
 */
public class Conditionnelle implements Operation {
	public final Condition condition;
	public final Algorithme siVrai;
	public final Algorithme siFaux;

	public Conditionnelle(Condition condition, Algorithme siVrai, Algorithme siFaux) {
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
	public boolean cumuler(List<Operation> nouveauxComposants) {
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
	public Operation simplifier() {
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
