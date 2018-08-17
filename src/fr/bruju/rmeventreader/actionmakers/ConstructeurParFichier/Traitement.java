package fr.bruju.rmeventreader.actionmakers.ConstructeurParFichier;

public interface Traitement {
	public Avancement traiter(String ligne);
	public Object resultat();
}
