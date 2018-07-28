package fr.bruju.rmeventreader.utilitaire.lambda;

public interface TriConsumer<A, B, C> {
	public void consume(A a, B b, C c);
}
