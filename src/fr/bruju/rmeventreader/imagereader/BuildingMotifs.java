package fr.bruju.rmeventreader.imagereader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.imagereader.model.MatricePixels;
import fr.bruju.rmeventreader.imagereader.model.Motif;
import fr.bruju.rmeventreader.imagereader.model.SymboleReconnus;
import fr.bruju.rmeventreader.imagereader.traitement.ChercheurDeMotifs;

/**
 * L'objectif de cette classe est de prendre une liste de monstres et de tenter de reconnaître leurs noms.
 * <p>
 * Pour cela, on suppose que le nom actuel du monstre est le nom de l'image où est écrit son nom.
 * <p>
 * <p>
 * Usage à partir d'une MonsterDatabase nommée db pour obtenir le fichier avec la liste des noms des monstres
 * <p>
 * <code>
 * List<String> nomDesImages = new ArrayList<>();
 * db.extractMonsters().forEach(m -> nomDesImages.add(m.getNom()));
 * BuildingMotifs chercheurDeMotifs = new BuildingMotifs(nomDesImages);
 * chercheurDeMotifs.lancer();
 * chercheurDeMotifs.getMap().forEach( (cle, valeur) -> System.out.println(cle + " " + valeur));
 * </code>
 */
public class BuildingMotifs {
	private static String CHEMIN_SUFFIXE = ".PNG"; // Suffixe du chemin où sont les images
	
	/**
	 * Chaines reconnues
	 */
	private Map<String, String> chainesReconnues;
	
	/** Dossier contenant les images */
	private final String dossier;
	
	
	public BuildingMotifs(String dossier, String[] nomsDesImages) {
		this.dossier = dossier;
		
		chainesReconnues = new HashMap<>();
		
		List<Motif> motifs = new SymboleReconnus().getMotifs();
		
		for (String nomImage : nomsDesImages) {
			if (!chainesReconnues.containsKey(nomImage)) {
				chainesReconnues.put(nomImage, lireUnNom(nomImage, motifs));
			}
		}
	}

	private String lireUnNom(String nomImage, List<Motif> motifs) {
		String filename = dossier + nomImage + CHEMIN_SUFFIXE;

		MatricePixels matrice;

		try {
			matrice = MatricePixels.lireImage(filename);
		} catch (IOException e) {
			System.out.println(filename + " n'existe probablement pas");
			e.printStackTrace();
			return null;
		}
		
		return new ChercheurDeMotifs(matrice, motifs).reconnaitre();
	}

	/**
	 * Permet de récupérer la liste des images de monstre reconnues avec les noms associés
	 * 
	 * @return Une map associant nom d'image et mot reconnu
	 */
	public Map<String, String> getMap() {
		return chainesReconnues;
	}
}
