package fr.bruju.rmeventreader.imagereader.model;

/**
 * Repr�sentation simplifi�e de l'image
 */
public class MatricePixels {
	/**
	 * Hauteur de l'image
	 */
	private int hauteur;

	/**
	 * Longueur de l'image
	 */
	private int longueur;

	/**
	 * Tableau montrant la liste des pixels o� le rouge est plus clair
	 */
	private boolean[][] pixelsAllumes;

	/**
	 * Construit un reconnaissuer de motifs � partir d'une matrice de pixels allum�s
	 * 
	 * @param hauteur Hauteur de l'image
	 * @param longueur Longueur de l'image
	 * @param pixelsAllumes Matrice avec la position des pixels allum�s
	 */
	public MatricePixels(int hauteur, int longueur, boolean[][] pixelsAllumes) {
		this.hauteur = hauteur;
		this.longueur = longueur;
		this.pixelsAllumes = pixelsAllumes;
	}

	/**
	 * Donne l'�tat du pixel voulu
	 * 
	 * @param x La coordonn�e x
	 * @param y La coordonn�e y
	 * @return Vrai si le pixel en x,y est allum�
	 */
	public boolean get(int x, int y) {
		return pixelsAllumes[x][y];
	}

	/**
	 * Renvoie la longueur de l'image
	 * 
	 * @return La longueur de l'image
	 */
	public int getLongueur() {
		return this.longueur;
	}

	/**
	 * Renvoie la hauteur de l'image
	 * 
	 * @return La hauteur de l'image
	 */
	public int getHauteur() {
		return this.hauteur;
	}

	/**
	 * Affiche la matrice des pixels reconnus
	 */
	public void afficher() {
		for (int ligne = 0; ligne != hauteur; ligne++) {
			for (int colonne = 0; colonne != longueur; colonne++) {
				System.out.print(pixelsAllumes[colonne][ligne] ? "X" : " ");
			}
			System.out.println();
		}
	}

}
