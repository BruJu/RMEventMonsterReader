package fr.bruju.rmeventreader.imagereader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.imagereader.model.MatricePixels;
import fr.bruju.rmeventreader.imagereader.model.Motif;
import fr.bruju.rmeventreader.imagereader.model.SymboleReconnus;
import fr.bruju.rmeventreader.imagereader.traitement.ChercheurDeMotifs;
import fr.bruju.rmeventreader.imagereader.traitement.ImageReader;

/**
 * L'objectif de cette classe est de prendre une liste de monstres et de tenter de reconnaître leurs noms.
 * 
 * Pour cela, on suppose que le nom actuel du monstre est le nom de l'image où est écrit son nom.
 * 
 * 
 * Usage à partir d'une MonsterDatabase nommée db pour obtenir le fichier avec la liste des noms des monstres
 * 
 * List<String> nomDesImages = new ArrayList<>();
 * db.extractMonsters().forEach(m -> nomDesImages.add(m.getNom()));
 * BuildingMotifs chercheurDeMotifs = new BuildingMotifs(nomDesImages);
 * chercheurDeMotifs.lancer();
 * chercheurDeMotifs.getMap().forEach( (cle, valeur) -> System.out.println(cle + " " + valeur));
 */
public class BuildingMotifs {
	private static String IGNORE_NAME = "UNKNOWN_NAME"; // Monstres dont l'image contenant le nom n'a pas pu être obtenue
	private static String CHEMIN_PREFIX = "ressources\\Picture\\"; // Préfixe du repertoire où sont les images
	private static String CHEMIN_SUFFIXE = ".PNG"; // Suffixe du chemin où sont les images

	/**
	 * Liste des monstres
	 */
	private List<String> nomImages;

	/**
	 * Chaines reconnues
	 */
	private Map<String, String> chainesReconnues;

	/**
	 * Crée un constructeur de motifs
	 * 
	 * @param monstres La liste des monstres à reconnaître
	 */
	public BuildingMotifs(List<String> nomImages) {
		this.nomImages = nomImages;
	}

	/**
	 * Permet de récupérer la liste des images de monstre reconnues avec les noms associés
	 * 
	 * @return Une map associant nom d'image et mot reconnu
	 */
	public Map<String, String> getMap() {
		return chainesReconnues;
	}

	/**
	 * Permet de lancer la reconnaissance des noms de monstres
	 */
	public void lancer() {
		chainesReconnues = new HashMap<>();

		List<Motif> motifs = new SymboleReconnus().getMotifs();

		for (String image : nomImages) {
			if (chainesReconnues.containsKey(image)) {
				continue;
			}

			if (image.equals(IGNORE_NAME)) {
				continue;
			}

			String filename = CHEMIN_PREFIX + image + CHEMIN_SUFFIXE;

			ImageReader ir = new ImageReader();

			MatricePixels matrice;

			try {
				matrice = ir.lireImage(filename);
			} catch (IOException e) {
				System.out.println(filename + " n'existe probablement pas");
				e.printStackTrace();
				continue;
			}
			ChercheurDeMotifs chercheurMotifs = new ChercheurDeMotifs(matrice, motifs);

			String s = chercheurMotifs.reconnaitre();

			chainesReconnues.put(image, s);
		}
	}
}
