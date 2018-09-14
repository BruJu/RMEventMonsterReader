package fr.bruju.rmeventreader.dictionnaires.modele;


import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.filereader.FileReaderByLine;

public class MapArbre {
	public final int idPere;
	public final String nom;
	
	public MapArbre(int idPere, String nom) {
		this.idPere = idPere;
		this.nom = nom;
	}
	
	public static List<MapArbre> extraireArbre(String fichierArbre) {
		List<MapArbre> resultat = new ArrayList<>();
		
		boolean r = FileReaderByLine.lectureFichierRessources(fichierArbre, ligne -> {
			String[] donnees = FileReaderByLine.splitter(ligne, 3);
			
			int parent = Integer.parseInt(donnees[1]);
			String nom = donnees[2];
			
			resultat.add(new MapArbre(parent, nom));
		});
		
		return r ? resultat : null;
	}
}
