package fr.bruju.rmeventreader.reconnaissancedimage;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Représentation simplifiée de l'image
 */
public class MatricePixels {
	/** Hauteur de l'image */
	public final int hauteur;

	/** Longueur de l'image */
	public final int longueur;

	/** Tableau montrant la liste des pixels où le rouge est plus clair */
	private boolean[][] pixelsAllumes;
	

	/**
	 * Construit un reconnaissuer de motifs à partir d'une matrice de pixels allumés
	 * 
	 * @param hauteur Hauteur de l'image
	 * @param longueur Longueur de l'image
	 * @param pixelsAllumes Matrice avec la position des pixels allumés
	 */
	public MatricePixels(int hauteur, int longueur, boolean[][] pixelsAllumes) {
		this.hauteur = hauteur;
		this.longueur = longueur;
		this.pixelsAllumes = pixelsAllumes;
	}

	/**
	 * Donne l'état du pixel voulu
	 * 
	 * @param x La coordonnée x
	 * @param y La coordonnée y
	 * @return Vrai si le pixel en x,y est allumé
	 */
	public boolean estAllume(int x, int y) {
		return pixelsAllumes[x][y];
	}

	/**
	 * Donne la matrice des pixels reconnus
	 * @return Une chaîne représentant l'image avec des croix pour les pixels considérés comme allumés et des espaces
	 * pour les pixels considérés éteints.
	 */
	public String getString() {
		StringBuilder[] renduDesLignes = new StringBuilder[hauteur];
		
		int premierLigneRemplie = -1;
		int derniereLigneRemplie = -2;
		
		for (int ligne = 0; ligne != hauteur ; ligne++) {
			boolean nonVide = false;
			StringBuilder sbLigne = new StringBuilder();
			
			for (int colonne = 0; colonne != longueur ; colonne++) {
				if (pixelsAllumes[colonne][ligne]) {
					sbLigne.append("X");
					nonVide = true;
				} else {
					sbLigne.append(" ");
				}
			}
			
			if (nonVide) {
				if (premierLigneRemplie == -1) {
					premierLigneRemplie = ligne;
				}
				
				derniereLigneRemplie = ligne;
			}
			
			renduDesLignes[ligne] = sbLigne;
		}
		
		StringBuilder stringBuilderGlobal = new StringBuilder();
		
		for (int i = premierLigneRemplie ; i <= derniereLigneRemplie ; i++) {
			StringBuilder ligne = renduDesLignes[i];
			stringBuilderGlobal.append(ligne).append("\n");
		}
		
		return stringBuilderGlobal.toString();
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
