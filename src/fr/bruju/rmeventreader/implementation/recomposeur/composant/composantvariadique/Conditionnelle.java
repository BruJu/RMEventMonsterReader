package fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.CaseMemoire;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.Variadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.BoutonVariadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionFixe;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.ValeurVariadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;
import java.util.Objects;

/**
 * Suite de composants variadiques dépendant d'une Cond()
 * 
 * @author Bruju
 *
 */
public abstract class Conditionnelle<T extends CaseMemoire> implements ComposantVariadique<Variadique<T>> {
	public static class Bouton
			extends Conditionnelle<fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.Bouton> {

		public final Condition condition;
		public final BoutonVariadique siVrai;
		public final BoutonVariadique siFaux;
		
		public Bouton(Condition condition,
				BoutonVariadique siVrai,
				BoutonVariadique siFaux) {
			this.condition = condition;
			this.siVrai = siVrai;
			this.siFaux = siFaux;
		}

		@Override
		protected ComposantVariadique<Variadique<fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.Bouton>> construire(
				Condition c, Variadique<fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.Bouton> v,
				Variadique<fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.Bouton> f) {
			return new Bouton(c, (BoutonVariadique) v, (BoutonVariadique) f);
		}

		@Override
		protected Condition Cond() {
			return condition;
		}

		@Override
		protected Variadique<fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.Bouton> SiVrai() {
			return siVrai;
		}

		@Override
		protected Variadique<fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.Bouton> SiFaux() {
			return siFaux;
		}
	}

	public static class Valeur
			extends Conditionnelle<fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Valeur> {

		public final Condition condition;
		public final ValeurVariadique siVrai;
		public final ValeurVariadique siFaux;
		
		public Valeur(Condition condition,
				ValeurVariadique siVrai,
				ValeurVariadique siFaux) {
			this.condition = condition;
			this.siVrai = siVrai;
			this.siFaux = siFaux;
		}

		@Override
		protected ComposantVariadique<Variadique<fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Valeur>> construire(
				Condition c, Variadique<fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Valeur> v,
				Variadique<fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Valeur> f) {
			return new Valeur(c, (ValeurVariadique) v, (ValeurVariadique) f);
		}

		@Override
		protected Condition Cond() {
			return condition;
		}

		@Override
		protected Variadique<fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Valeur> SiVrai() {
			return siVrai;
		}

		@Override
		protected Variadique<fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Valeur> SiFaux() {
			return siFaux;
		}
	}
	/* =========
	 * COMPOSANT
	 * ========= */

	/** Condition */
	protected abstract Condition Cond();

	/** Valeur si la condition est vérifiée */
	protected abstract Variadique<T> SiVrai();

	/** Valeur si la condition n'est pas vérifiée */
	protected abstract Variadique<T> SiFaux();

	/* ================
	 * AFFICHAGE SIMPLE
	 * ================ */

	@Override
	public String toString() {
		return "[" + Cond().toString() + " ? " + SiVrai().toString() + " : " + SiFaux().toString() + "]";
	}

	/* ========
	 * VISITEUR
	 * ======== */

	@Override
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}

	@Override
	public ComposantVariadique<Variadique<T>> simplifier() {
		Condition cSimplifiee = Cond();

		Boolean identifie = ConditionFixe.identifier(cSimplifiee);

		if (identifie != null) {
			if (identifie) {
				return construire(ConditionFixe.get(true), SiVrai().simplifier(), null);
			} else {
				return construire(ConditionFixe.get(true), SiFaux().simplifier(), null);
			}
		} else {
			return this;
		}
	}

	
	protected abstract ComposantVariadique<Variadique<T>> construire(Condition cSimplifiee, Variadique<T> vraiSimplifie,
			Variadique<T> fauxSimplifie);

	
	/* =================
	 * EQUALS / HASHCODE
	 * ================= */

	@Override
	public int hashCode() {
		return Objects.hash(Cond(), SiVrai(), SiFaux());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object object) {
		if (object instanceof Conditionnelle) {
			Conditionnelle that = (Conditionnelle) object;
			return Objects.equals(this.Cond(), that.Cond()) && Objects.equals(this.SiVrai(), that.SiVrai())
					&& Objects.equals(this.SiFaux(), that.SiFaux());
		}
		return false;
	}
}
