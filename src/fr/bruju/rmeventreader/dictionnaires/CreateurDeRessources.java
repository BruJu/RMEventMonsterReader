package fr.bruju.rmeventreader.dictionnaires;

import java.util.List;

import fr.bruju.rmeventreader.utilitaire.Utilitaire;

public class CreateurDeRessources {
	private String destination;

	public CreateurDeRessources(String destination) {
		this.destination = destination;
	}

	public void extraireBDD() {
		ecrireRessource("actors", "bdd_heros");
		ecrireRessource("items", "bdd_objets");
		ecrireRessource("switches", "bdd_switch");
		ecrireRessource("variables", "bdd_variables");
	}

	private void ecrireRessource(String nomGroupe, String nomFichier) {
		List<String> dico = FabriqueMiLCFMiXML.getInstance().getListeDeNoms(nomGroupe);

		String chemin = destination + nomFichier + ".txt";
		
		StringBuilder sb = new StringBuilder();
		for (String valeur : dico) {
			sb.append(valeur + "\n");
		}
		
		Utilitaire.Fichier_Ecrire(chemin, sb.toString());
	}
}
