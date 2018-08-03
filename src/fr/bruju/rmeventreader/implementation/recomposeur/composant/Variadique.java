package fr.bruju.rmeventreader.implementation.recomposeur.composant;

public interface Variadique<T extends CaseMemoire> extends Element {

	@Override
	public Variadique<T> simplifier();
	
	
	//public T reconstituer();
	

}
