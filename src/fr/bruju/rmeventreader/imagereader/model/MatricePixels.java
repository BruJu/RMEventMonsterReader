package fr.bruju.rmeventreader.imagereader.model;

/**
 * Représentation simplifiée de l'image
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
	 * Tableau montrant la liste des pixels où le rouge est plus clair
	 */
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
