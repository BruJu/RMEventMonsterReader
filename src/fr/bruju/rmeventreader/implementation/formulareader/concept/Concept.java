package fr.bruju.rmeventreader.implementation.formulareader.concept;

public interface Concept {

	public String getNom();
	
	public boolean estSimilaire(Concept autre);
	
	public boolean estIdentique(Concept autre);
}
