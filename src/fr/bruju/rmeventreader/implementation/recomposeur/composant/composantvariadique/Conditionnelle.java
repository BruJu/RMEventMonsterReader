package fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.CaseMemoire;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.Variadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.BoutonVariadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionFixe;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Valeur;
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
public abstract class Conditionnelle<T extends CaseMemoire> implements ComposantVariadique<Variadique<T>> {
	public static class CBouton
			extends Conditionnelle<fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.Bouton> {

		public final Condition condition;
		public final BoutonVariadique siVrai;
		public final BoutonVariadique siFaux;

		public CBouton(Condition condition, BoutonVariadique siVrai, BoutonVariadique siFaux) {
			this.condition = condition;
			this.siVrai = siVrai;
			this.siFaux = siFaux;
		}

		@Override
		protected ComposantVariadique<Variadique<Bouton>> construire(Condition c, Variadique<Bouton> v,
				Variadique<Bouton> f) {
			return new CBouton(c, (BoutonVariadique) v, (BoutonVariadique) f);
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

		@Override
		protected void sousCumul(Boolean identifie,
				List<ComposantVariadique<? extends Variadique<Bouton>>> nouveauxComposants) {
			nouveauxComposants.addAll(identifie ? siVrai.composants : siFaux.composants);
		}
	}

	public static class CValeur
			extends Conditionnelle<fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Valeur> {

		public final Condition condition;
		public final ValeurVariadique siVrai;
		public final ValeurVariadique siFaux;

		public CValeur(Condition condition, ValeurVariadique siVrai, ValeurVariadique siFaux) {
			this.condition = condition;
			this.siVrai = siVrai;
			this.siFaux = siFaux;
		}

		@Override
		protected ComposantVariadique<Variadique<Valeur>> construire(Condition c, Variadique<Valeur> v,
				Variadique<Valeur> f) {
			return new CValeur(c, (ValeurVariadique) v, (ValeurVariadique) f);
		}

		@Override
		protected Condition Cond() {
			return condition;
		}

		@Override
		protected Variadique<Valeur> SiVrai() {
			return siVrai;
		}

		@Override
		protected Variadique<Valeur> SiFaux() {
			return siFaux;
		}

		@Override
		protected void sousCumul(Boolean identifie,
				List<ComposantVariadique<? extends Variadique<Valeur>>> nouveauxComposants) {
			nouveauxComposants.addAll(identifie ? siVrai.composants : siFaux.composants);
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

	@Override
	public boolean cumuler(List<ComposantVariadique<? extends Variadique<T>>> nouveauxComposants) {

		Boolean identifie = ConditionFixe.identifier(Cond());

		if (identifie == null) {
			nouveauxComposants.add(this);
			return true;
		}

		sousCumul(identifie, nouveauxComposants);

		return false;
	}

	protected abstract void sousCumul(Boolean identifie,
			List<ComposantVariadique<? extends Variadique<T>>> nouveauxComposants);

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
