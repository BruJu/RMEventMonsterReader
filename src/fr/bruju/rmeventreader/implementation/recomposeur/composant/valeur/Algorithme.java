package fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.Element;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.ElementIntermediaire;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.Affectation;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.Operation;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;

public class Algorithme implements Valeur, ElementIntermediaire {
	/* =========
	 * COMPOSANT
	 * ========= */
	/** Liste des opérations */
	public final List<Operation> composants;
	
	/**
	 * Crée un bouton variadique vide
	 */
	public Algorithme() {
		this.composants = new ArrayList<>();
	}
 
	public Algorithme(List<Operation> sousElements) {
		this.composants = Collections.unmodifiableList(sousElements);
	}

	public Algorithme(Element[] tableau) {
		composants = Stream.of(tableau).map(element -> (Operation) element)
					.collect(Collectors.toList());
	}
	
	public Algorithme(Algorithme base, Operation operation) {
		List<Operation> intermediaire = new ArrayList<>();
		if (base != null) {
			intermediaire.addAll(base.composants);
		}
		intermediaire.add(operation);
		
		this.composants = Collections.unmodifiableList(intermediaire);
	}

	
	public Algorithme(Valeur valeur) {
		this(null, new Affectation(valeur));
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
		if (object instanceof Algorithme) {
			Algorithme that = (Algorithme) object;
			return Objects.deepEquals(this.composants, that.composants);
		}
		return false;
	}

	/* ==============
	 * SIMPLIFICATION
	 * ============== */

	@Override
	public Algorithme simplifier() {
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
		return new Algorithme(fils);
	}

	@Override
	public Algorithme toAlgorithme() {
		return this;
	}
}
