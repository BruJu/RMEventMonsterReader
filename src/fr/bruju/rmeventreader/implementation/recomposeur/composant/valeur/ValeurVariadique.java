package fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.Element;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.Variadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.ComposantVariadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;

public class ValeurVariadique extends Variadique<Valeur> implements Valeur {
	/* =========
	 * COMPOSANT
	 * ========= */
	/** Liste des opérations */
	public final List<ComposantVariadique<ValeurVariadique>> composants;
	
	/**
	 * Crée un bouton variadique vide
	 */
	public ValeurVariadique() {
		this.composants = new ArrayList<>();
	}

	public ValeurVariadique(List<ComposantVariadique<ValeurVariadique>> sousElements) {
		this.composants = Collections.unmodifiableList(sousElements);
	}

	@SuppressWarnings("unchecked")
	public ValeurVariadique(Element[] tableau) {
		composants = Stream.of(tableau).map(element -> (ComposantVariadique<ValeurVariadique>) element)
					.collect(Collectors.toList());
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
		if (object instanceof ValeurVariadique) {
			ValeurVariadique that = (ValeurVariadique) object;
			return Objects.deepEquals(this.composants, that.composants);
		}
		return false;
	}

	/* ==============
	 * SIMPLIFICATION
	 * ============== */

	@Override
	public ValeurVariadique simplifier() {
		return null;
	}

}
