package fr.bruju.rmeventreader.actionmakers.composition.composant;


public interface ElementIntermediaire extends Element {
	public Element[] getFils();
	public ElementIntermediaire fonctionDeRecreation(Element[] elementsFils);
}
