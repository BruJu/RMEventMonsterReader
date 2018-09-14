package fr.bruju.rmeventreader.dictionnaires.modele;

import java.util.List;

import fr.bruju.rmeventreader.filereader.FileReaderByLine;

public class MapArbre {
	public final int idPere;
	public final String nom;
	
	public MapArbre(int idPere, String nom) {
		this.idPere = idPere;
		this.nom = nom;
	}
	
	public static MapArbre[] extraireArbre(String fichierArbre) {
		List<String[]> s = FileReaderByLine.lireFichier(fichierArbre, 3);
		
		if (s == null)
			return null;

		
		MapArbre[] arbre = new MapArbre[s.size()];
		
		for (int i = 0 ; i != s.size() ; i++) {
			arbre[i] = new MapArbre(Integer.parseInt(s.get(i)[1]), s.get(i)[2]);
		}
		
		return arbre;
	}
}
