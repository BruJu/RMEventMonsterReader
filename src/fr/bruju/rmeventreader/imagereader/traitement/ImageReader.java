package fr.bruju.rmeventreader.imagereader.traitement;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import fr.bruju.rmeventreader.imagereader.model.MatricePixels;

/**
 * Cette classe permet de lire une image et de reconna�tre les pixels allum�s.
 */
public class ImageReader {
	/**
	 * Lit l'image donn�e et reconnait les pixels qui sont allum�s Un pixel est consid�r� comme allum� si sa composante
	 * de rouge est sup�rieure � 50
	 * 
	 * @param chemin Le chemin vers l'image
	 * @return Un objet contenant une matrice avec les pixels allum�s
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
