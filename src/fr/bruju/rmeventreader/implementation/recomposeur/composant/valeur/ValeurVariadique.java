package fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.Element;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.ElementIntermediaire;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.ComposantVariadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;

public class ValeurVariadique implements Valeur, ElementIntermediaire {
	/* =========
	 * COMPOSANT
	 * ========= */
	/** Liste des opérations */
	public final List<ComposantVariadique> composants;
	
	/**
	 * Crée un bouton variadique vide
	 */
	public ValeurVariadique() {
		this.composants = new ArrayList<>();
	}
 
	public ValeurVariadique(List<ComposantVariadique> sousElements) {
		this.composants = Collections.unmodifiableList(sousElements);
	}

	public ValeurVariadique(Element[] tableau) {
		composants = Stream.of(tableau).map(element -> (ComposantVariadique) element)
					.collect(Collectors.toList());
	}
	
	
	/* ===============
	 * IMPLEMENTATIONS
	 * =============== */

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		composants.forEach(action -> sb.append("<").append(action.toString()).append(">"));
		return sb.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(composants);
	}

	/* ==============
	 * SIMPLIFICATION
	 * ============== */
	
	
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

	/* ==============
	 * SIMPLIFICATION
	 * ============== */
	
	@Override
	public Element[] getFils() {
		return composants.toArray(new Element[0]);
	}
	
	@Override
	public ElementIntermediaire fonctionDeRecreation(Element[] fils) {
		return new ValeurVariadique(fils);
	}
}
