package fr.bruju.rmeventreader.imagereader.traitement;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import fr.bruju.rmeventreader.imagereader.model.MatricePixels;

/**
 * Cette classe permet de lire une image et de reconnaître les pixels allumés.
 */
public class ImageReader {
	/**
	 * Lit l'image donnée et reconnait les pixels qui sont allumés Un pixel est considéré comme allumé si sa composante
	 * de rouge est supérieure à 50
	 * 
	 * @param chemin Le chemin vers l'image
	 * @return Un objet contenant une matrice avec les pixels allumés
	 * @throws IOException
	 */
	public MatricePixels lireImage(String chemin) throws IOException {
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
