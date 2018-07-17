package fr.bruju.rmeventreader.implementation.formulareader.concept;

public class VariableNommee implements Concept {

	private String nom;

	public VariableNommee(String nom) {
		this.nom = nom;
	}
	
	@Override
	public String getNom() {
		return nom;
	}

	@Override
	public boolean estSimilaire(Concept autre) {
		return estIdentique(autre);
	}

	@Override
	public boolean estIdentique(Concept autre) {
		if (!(autre instanceof VariableNommee))
			return false;
		
		return this.getNom().equals(autre.getNom());
	}

}
