package fr.bruju.rmeventreader.actionmakers.ConstructeurParFichier;

public class TableauInt implements Traitement {
	private String nomChamp;
	private int[] valeur;
	
	public TableauInt(String nomChamp) {
		this.nomChamp = nomChamp;
	}
	
	@Override
	public Avancement traiter(String ligne) {
		String[] split = ligne.split(" ");
		
		int debut = 0;
		
		if (nomChamp != null) {
			if (split.length == 0 || !split[0].equals(nomChamp))
				return Avancement.Tuer;
			
			debut = 1;
		}
		
		valeur = new int[split.length - debut];
		
		for (int i = debut ; i != split.length ; i++) {
			valeur[i - debut] = Integer.parseInt(split[i]);
		}
		
		return Avancement.Suivant;
	}

	@Override
	public int[] resultat() {
		return valeur;
	}
}
