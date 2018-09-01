package fr.bruju.rmeventreader.utilitaire;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Classe possédant deux éléments. Non mutable.
 * 
 * @author Bruju
 *
 * @param <T1> Type du premier élément
 * @param <T2> Type du second élément
 */
public class Pair<T1, T2> {
	/** Premier élément */
	private final T1 t1;
	/** Second élément */
	private final T2 t2;
	
	/**
	 * Crée une paire
	 * @param t1 Premier élément
	 * @param t2 Second élément
	 */
	public Pair(T1 t1, T2 t2) {
		this.t1 = t1;
		this.t2 = t2;
	}
	
	/**
	 * Donne le premier élément
	 * @return Le premier élément
	 */
	public T1 getLeft() {
		return t1;
	}
	
	/**
	 * Donne le second élément
	 * @return Le second élément
	 */
	public T2 getRight() {
		return t2;
	}

	@Override
	public String toString() {
		return "{" + t1 + ", " + t2 + "}";
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
	

	/**
	 * Donne la partie gauche de la paire
	 */
	public static <K, V> K k(Pair<K, V> paire) {
		return paire.getLeft();
	}

	/**
	 * Donne la partie droite de la paire
	 */
	public static <K, V> V v(Pair<K, V> paire) {
		return paire.getRight();
	}
	
	/**
	 * Collecteur permettant de transformer les paires d'un stream en map
	 */
	public static <T,K,U> Collector<Pair<K, U>,?,Map<K,U>> toMap() {
		return Collectors.toMap(Pair::k, Pair::v);
	}
	
	
}
