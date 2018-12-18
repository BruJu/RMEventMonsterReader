package fr.bruju.rmeventreader.implementation.monsterlist;

import fr.bruju.util.reconnaissancedimage.ChercheurDeMotifs;
import fr.bruju.util.reconnaissancedimage.MatricePixels;
import fr.bruju.util.reconnaissancedimage.Motif;
import fr.bruju.rmeventreader.utilitaire.LecteurDeFichiersLigneParLigne;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * L'objectif de cette classe est de prendre une liste de monstres et de tenter de reconnaître leurs noms.
 * <p>
 * Pour cela, on suppose que le nom actuel du monstre est le nom de l'image où est écrit son nom.
 * <p>
 * <p>
 * Usage à partir d'une MonsterDatabase nommée db pour obtenir le fichier avec la liste des noms des monstres
 */
public class ReconnaisseurDImages {
	/** Chemin vers la liste des motifs connus */
	private static final String CHEMIN_MOTIFS_CONNUS = "metaressources/ocr/motifsconnus.txt";

	/** Extension des fichiers image */
	private static final String CHEMIN_SUFFIXE = ".PNG";
	
	/** Chaines reconnues */
	private Map<String, String> chainesReconnues;
	
	/** Chaînes non reconnues */
	private StringJoiner fichiersIntrouvables = new StringJoiner(", ");
	private StringJoiner fichiersIOException = new StringJoiner(", ");
	private List<String> fichiersSymbolesManquants = new ArrayList<>();
	
	/** Dossier contenant les images */
	private final String dossier;
	
	/** Motifs connus */
	private List<Motif> motifs;
	
	/** Liste des motifs non reconnus */
	private List<Motif> motifsInconnus = new ArrayList<>();

	/**
	 * Crée un reconnaisseur de nom de monstres en utilisant les images contenues dans le dossier spécifié
	 * @param dossier Le dossier contenant les images décrivant les noms des monstres
	 */
	public ReconnaisseurDImages(String dossier) {
		this.dossier = dossier;
		this.chainesReconnues = new HashMap<>();

		this.motifs = LecteurDeFichiersLigneParLigne.listerRessources(CHEMIN_MOTIFS_CONNUS, Motif::new);
		if (this.motifs == null) {
			this.motifs = new ArrayList<>();
		}
	}

	/**
	 * Tente d'identifier les lettres écrites sur l'image, et ajoute le nom du monstre à la base de connaissances
	 * @param nomImage Le nom de l'image
	 */
	public void identifier(String nomImage) {
		if (chainesReconnues.containsKey(nomImage)) {
			return;
		}
		
		String chemin = dossier + nomImage + CHEMIN_SUFFIXE;
		String nomIdentifie = null;
		
		MatricePixels matrice;

		try {
			matrice = lireImage(chemin);
			nomIdentifie = new ChercheurDeMotifs(matrice, motifs, motifsInconnus).reconnaitre();
			
			if (nomIdentifie == null) {
				fichiersSymbolesManquants.add(matrice.getString());
			}
		} catch (FileNotFoundException e) {
			fichiersIntrouvables.add(nomImage);
		} catch (IOException e) {
			fichiersIOException.add(nomImage);
			
			e.printStackTrace();
		}

		chainesReconnues.put(nomImage, nomIdentifie);
	}

	/**
	 * Donne la liste des motifs inconnus
	 * @return La liste des motifs inconnus
	 */
	public List<Motif> getMotifsInconnus() {
		return motifsInconnus;
	}

	/**
	 * Donne la liste de tous les noms identifiés avec le nom qui a été lu
	 * @return Une liste de toutes les images identifiés avec la chaîne lue
	 */
	public String getNomsIdentifies() {
		return chainesReconnues.entrySet()
							   .stream()
							   .filter(entree -> entree.getValue() != null)
							   .map(entree -> entree.getKey() + " " + entree.getValue())
							   .collect(Collectors.joining("\n"));
	}
	
	/**
	 * Donne une chaîne lisant toutes les erreurs lors de la lecture
	 * @return Une chaîne avec la liste des fichiers manquants, des fichiers en erreur et les fichiers dont il manque
	 * des symboles pour les reconnaître
	 */
	public String listeLesErreurs() {
		StringBuilder sb = new StringBuilder();
		
		if (fichiersIntrouvables.length() != 0) {
			sb.append("Fichiers Manquants : ").append(fichiersIntrouvables.toString());
		}

		if (fichiersIOException.length() != 0) {
			sb.append("Fichiers ayant jeté une exception : ").append(fichiersIOException.toString());
		}

		if (fichiersSymbolesManquants.size() != 0) {
			sb.append("Fichiers avec des symboles inconnus :\n");
			fichiersSymbolesManquants.forEach(image -> sb.append(image).append("\n"));
			
		}
		
		return sb.toString();
	}

	/**
	 * Lit l'image donnée et reconnait les pixels qui sont allumés. Un pixel est considéré comme allumé si sa composante
	 * de rouge est supérieure à 50
	 *
	 * @param chemin Le chemin vers l'image
	 * @return Un objet contenant une matrice avec les pixels allumés
	 * @throws IOException
	 */
	public static MatricePixels lireImage(String chemin) throws IOException {
		File file = new File(chemin);

		BufferedImage image = ImageIO.read(file);

		int hauteur = image.getHeight();
		int longueur = image.getWidth();

		boolean[][] pixelsAllumes = new boolean[longueur][hauteur];

		for (int x = 0; x != longueur; x++) {
			for (int y = 0; y != hauteur; y++) {
				Color color = new Color(image.getRGB(x, y));

				pixelsAllumes[x][y] = color.getRed() > 50;
			}
		}

		return new MatricePixels(hauteur, longueur, pixelsAllumes);
	}
}
