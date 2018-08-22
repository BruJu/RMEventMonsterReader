package fr.bruju.rmeventreader.dictionnaires.liblcfreader;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import fr.bruju.rmeventreader.utilitaire.Utilitaire;

public class CreateurDeRessources {
	private String destination;

	public CreateurDeRessources(String destination) {
		this.destination = destination;
	}


	public void extraireBDD(String fichier) {
		ecrireRessource(fichier, "actors", "Actor", "bdd_heros");
		ecrireRessource(fichier, "items", "Item", "bdd_objets");
		ecrireRessource(fichier, "switches", "Switch", "bdd_switch");
		ecrireRessource(fichier, "variables", "Variable", "bdd_variables");
		ecrireRessource(fichier, "commonevents", "CommonEvent", "bdd_ec");
	}

	private void ecrireRessource(String fichier, String nomGroupe, String nomElement, String nomFichier) {
		String[] dico = new ExtractionXML().extraireDictionnaire(fichier, nomGroupe, nomElement);

		String chemin = destination + nomFichier + ".txt";
		
		StringBuilder sb = new StringBuilder();
		for (String valeur : dico) {
			sb.append(valeur + "\n");
		}
		
		Utilitaire.Fichier_Ecrire(chemin, sb.toString());
	}
	
	
	
	
	public void extraireArbre(String arbreXML) {
		LinkedHashMap<String, String[]> map = ExtractionXML.extraireDonnees(arbreXML, "/LMT/TreeMap/maps/MapInfo",
				new String[] { "name", "parent_map" });
		
		String chemin = destination + "bdd_maps" + ".txt";

		StringBuilder sb = new StringBuilder();
		
		for (Entry<String, String[]> donnees : map.entrySet()) {
			sb.append(donnees.getKey() + " ");

			sb.append(donnees.getValue()[1] + " ");
			sb.append(donnees.getValue()[0] + "\n");
		}
		
		Utilitaire.Fichier_Ecrire(chemin, sb.toString());		
	}

}
