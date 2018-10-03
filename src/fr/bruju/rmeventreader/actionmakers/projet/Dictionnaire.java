package fr.bruju.rmeventreader.actionmakers.projet;

import java.util.List;

import fr.bruju.rmeventreader.utilitaire.Utilitaire;

public class Dictionnaire {
	private List<String> donneesExtraites;

	public Dictionnaire(List<String> listeDeNoms) {
		donneesExtraites = listeDeNoms;
	}

	public String extraire(int index) {
		return donneesExtraites.get(index - 1);
	}

	public void ecrireFichier(String fichierDestination) {
		StringBuilder sb = new StringBuilder();
		for (String valeur : donneesExtraites) {
			sb.append(valeur + "\n");
		}
		
		Utilitaire.Fichier_Ecrire(fichierDestination, sb.toString());
	}
}