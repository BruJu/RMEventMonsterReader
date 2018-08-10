package fr.bruju.rmeventreader.utilitaire;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
	public String toString() {
		return "{" + t1 + ", " + t2 + "}";
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

	@Override
	public int hashCode() {
		return Objects.hash(t1, t2);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object object) {
		if (object instanceof Pair) {
			Pair that = (Pair) object;
			return Objects.equals(this.t1, that.t1) && Objects.equals(this.t2, that.t2);
		}
		return false;
	}
	
	
	public static <K, V> K k(Pair<K, V> paire) {
		return paire.getLeft();
	}

	public static <K, V> V v(Pair<K, V> paire) {
		return paire.getRight();
	}
	
	public static <T,K,U> Collector<Pair<K, U>,?,Map<K,U>> toMap() {
		return Collectors.toMap(Pair::k, Pair::v);
	}
	
	
}
