package fr.bruju.rmeventreader.utilitaire.lambda;

public interface TriFunction<A, B, C, R> {
	public R apply(A a, B b, C c);
}
