package fr.bruju.rmeventreader.implementation.magasin.objet;

public class Livre extends Objet {
	public final StatistiqueDeLivre statistique;

	public Livre(int id, String nom, StatistiqueDeLivre statistique) {
		super(id, nom);
		this.statistique = statistique;
	}

	@Override
	public String getString() {
		return id + " " + nom + " (£ " + statistique + ")";
	}

	public enum StatistiqueDeLivre {
		Erudition,
		Troc,
		Force,
		Magie,
		Esprit,
		Persuasion,
		Vitalite,
		Dexterite,
		Commandement
	}

}
