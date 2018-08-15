package fr.bruju.rmeventreader.dictionnaires;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class CreateurDeRessources {
	private String destination;

	public CreateurDeRessources(String destination) {
		this.destination = destination;
	}

	public void ecrireRessource(String fichier, String nomGroupe, String nomElement, String nomFichier) {
		String[] dico = new ExtractionXML().extraireDictionnaire(fichier, nomGroupe, nomElement);

		File f = new File(destination + nomFichier + ".txt");

		try {
			if (f.exists())
				f.delete();

			f.createNewFile();
			FileWriter ff = new FileWriter(f);

			for (String valeur : dico)
				ff.write(valeur + "\n");

			ff.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void extraireBDD(String fichier) {
		ecrireRessource(fichier, "actors", "Actor", "bdd_heros");
		ecrireRessource(fichier, "items", "Item", "bdd_objets");
		ecrireRessource(fichier, "switches", "Switch", "bdd_switch");
		ecrireRessource(fichier, "variables", "Variable", "bdd_variables");
	}

	public void extraireArbre(String arbreXML) {
		LinkedHashMap<String, String[]> map = ExtractionXML.extraireDonnees(arbreXML, "/LMT/TreeMap/maps/MapInfo",
				new String[] { "name", "parent_map" });
		
		
		File f = new File(destination + "bdd_maps" + ".txt");

		try {
			if (f.exists())
				f.delete();

			f.createNewFile();
			FileWriter ff = new FileWriter(f);
			
			for (Entry<String, String[]> donnees : map.entrySet()) {
				ff.write(donnees.getKey() + " ");

				ff.write(donnees.getValue()[1] + " ");
				ff.write(donnees.getValue()[0] + "\n");
			}
			

			ff.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
