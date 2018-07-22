package fr.bruju.rmeventreader.utilitaire.lambda;

public interface IntBiObjOperator<T> {
	public T apply(int value, T elem1, T elem2);
}
