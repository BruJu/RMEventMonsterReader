package fr.bruju.rmeventreader.actionmakers.ConstructeurParFichier;

public class PaireIDString implements Traitement {
	private String nomChamp;
	private String valeur;
	
	public PaireIDString(String nomChamp) {
		this.nomChamp = nomChamp;
	}

	@Override
	public Avancement traiter(String ligne) {
		String[] split = ligne.split(" ");
		
		if (split.length != 2 || !split[0].equals(nomChamp))
			return Avancement.Tuer;
		
		valeur = split[1];
		
		return Avancement.Suivant;
	}

	@Override
	public String resultat() {
		return valeur;
	}
}
