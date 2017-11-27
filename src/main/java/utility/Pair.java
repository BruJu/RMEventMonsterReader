package utility;

/**
 * Classe permettant de coupler deux éléments
 *
 * @param <A> Type du premier élément
 * @param <B> Type du second élément
 */
public class Pair<A, B> {
	private final A a;
	private final B b;
	
	/**
	 * Crée un couple contenant A et B
	 * @param a Le premier élément
	 * @param b Le second élément
	 */
	public Pair(A a, B b) {
		this.a = a;
		this.b = b;
	}
	
	/**
	 * Renvoie le premier élément du couple
	 * @return Le premier élément du couple
	 */
	public A getLeft() {
		return a;
	}

	/**
	 * Renvoie le premier élément du couple
	 * @return Le premier élément du couple
	 */
	public A l() {
		return a;
	}

	/**
	 * Renvoie le second élément du couple
	 * @return Le second élément du couple
	 */
	public B getRight() {
		return b;
	}
	
	/**
	 * Renvoie le second élément du couple
	 * @return Le second élément du couple
	 */
	public B r() {
		return b;
	}
}
