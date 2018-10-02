package fr.bruju.rmeventreader.implementation.recomposeur.composant.operation;

import java.util.List;
import java.util.Objects;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionFixe;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.recomposeur.visiteur.template.Visiteur;

/**
 * Suite de composants variadiques dépendant d'une condition
 * 
 * @author Bruju
 *
 */
public final class Conditionnelle implements Operation {
	public final Condition condition;
	public final Algorithme siVrai;
	public final Algorithme siFaux;

	public Conditionnelle(Condition condition, Algorithme siVrai, Algorithme siFaux) {
		this.condition = condition;
		this.siVrai = siVrai;
		this.siFaux = siFaux;
		
		if (ConditionFixe.identifier(condition) != null) {
			throw new RuntimeException("Création d'une conditionnelle avec une condition déjà évaluée.");
		}
	}

	/**
	 * Crée une conditionnelle avec la condition donnée. Si la condition est déjà évaluée, renvoie un Sous Algorithme
	 */
	public static Operation creerConditionnelle(Condition condition, Algorithme siVrai, Algorithme siFaux) {
		Boolean ident = ConditionFixe.identifier(condition);
		
		if (ident == null) {
			return new Conditionnelle(condition, siVrai, siFaux);
		} else {
			return new SousAlgorithme(ident ? siVrai : siFaux);
		}
	}

	/* ================
	 * AFFICHAGE SIMPLE
	 * ================ */

	@Override
	public String toString() {
		return "{" + condition.toString() + " ? " + siVrai.toString() + " : " + siFaux.toString() + "}";
	}

	@Override
	public boolean cumuler(List<Operation> nouveauxComposants) {
		nouveauxComposants.add(this);
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
			return new SousAlgorithme(identifie ? siVrai : siFaux);
		} else if (siVrai.equals(siFaux)) {
			return new SousAlgorithme(siVrai);
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
