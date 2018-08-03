package fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.Element;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.Variadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.ComposantVariadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Représente une série de traitement sur un interrupteur
 * 
 * @author Bruju
 *
 */
public class BoutonVariadique extends Variadique<Bouton> implements Bouton {
	/* =========
	 * COMPOSANT
	 * ========= */
	/** Liste des opérations */
	public final List<ComposantVariadique<BoutonVariadique>> composants;
	
	/**
	 * Crée un bouton variadique vide
	 */
	public BoutonVariadique() {
		this.composants = new ArrayList<>();
	}

	public BoutonVariadique(List<ComposantVariadique<BoutonVariadique>> sousElements) {
		this.composants = Collections.unmodifiableList(sousElements);
	}

	@Override
	public List<? extends Element> getComposants() {
		return composants;
	}

	/* ========
	 * VISITEUR
	 * ======== */
	
	@Override
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}

	/* =================
	 * EQUALS / HASHCODE
	 * ================= */

	@Override
	public boolean equals(Object object) {
		if (object instanceof BoutonVariadique) {
			BoutonVariadique that = (BoutonVariadique) object;
			return Objects.deepEquals(this.composants, that.composants);
		}
		return false;
	}

	/* ==============
	 * SIMPLIFICATION
	 * ============== */

	@Override
	public BoutonVariadique simplifier() {
		// TODO
		return null;
	}


}
