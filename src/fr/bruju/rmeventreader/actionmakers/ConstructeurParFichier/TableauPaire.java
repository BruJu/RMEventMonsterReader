package fr.bruju.rmeventreader.actionmakers.ConstructeurParFichier;

import fr.bruju.rmeventreader.utilitaire.Pair;

public class TableauPaire implements Traitement {
	private String nomChamp;
	private int[] valeur;
		
	@Override
	public Avancement traiter(String ligne) {
		String[] split = ligne.split(" ");
		
		
		nomChamp = split[0];
		
		valeur = new int[split.length - 1];
		
		for (int i = 1 ; i != split.length ; i++) {
			valeur[i - 1] = Integer.parseInt(split[i]);
		}
		
		return Avancement.Suivant;
	}

	@Override
	public Pair<String, int[]> resultat() {
		return new Pair<>(nomChamp, valeur);
	}
}
