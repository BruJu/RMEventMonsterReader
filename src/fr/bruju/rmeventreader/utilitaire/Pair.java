package fr.bruju.rmeventreader.utilitaire;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.Valeur;

public class Pair<T1, T2> {
	private final T1 t1;
	private final T2 t2;
	
	public Pair(T1 t1, T2 t2) {
		this.t1 = t1;
		this.t2 = t2;
	}
	
	public T1 getLeft() {
		return t1;
	}
	
	public T2 getRight() {
		return t2;
	}

	@Override
	public int hashCode() {
		return Objects.hash(t1, t2);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair other = (Pair) obj;
		if (t1 == null) {
			if (other.t1 != null)
				return false;
		} else if (!t1.equals(other.t1))
			return false;
		if (t2 == null) {
			if (other.t2 != null)
				return false;
		} else if (!t2.equals(other.t2))
			return false;
		return true;
	}

	public static <T1, T2> List<Pair<T1, T2>> combiner(List<T1> gauches, List<T2> droites) {
		if (gauches.size() != droites.size()) {
			return null;
		}
		
		ArrayList<Pair<T1, T2>> liste = new ArrayList<>(gauches.size());
		
		for (int i = 0 ; i != gauches.size(); i++) {
			liste.add(new Pair<T1, T2>(gauches.get(i), droites.get(i)));
		}
		
		return liste;
	}
	
	
}
