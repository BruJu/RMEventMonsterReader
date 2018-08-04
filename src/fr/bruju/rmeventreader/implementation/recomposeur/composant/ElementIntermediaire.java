package fr.bruju.rmeventreader.implementation.recomposeur.composant;


public interface ElementIntermediaire extends Element {
	public Element[] getFils();
	public ElementIntermediaire fonctionDeRecreation(Element[] elementsFils);
}
