package fr.bruju.rmeventreader.imagereader.traitement;

import java.util.List;

import fr.bruju.rmeventreader.imagereader.model.MatricePixels;
import fr.bruju.rmeventreader.imagereader.model.Motif;

/**
 * Cette classe recherche des motifs dans une matrice de pixels
 */
public class ChercheurDeMotifs {

	/**
	 * Nombre de colonnes vides pour consid�rer que c'est un espace
	 */
	private static int LARGEUR_ESPACE = 4;

	/**
	 * Matrice de pixels � fouiller
	 */
	private MatricePixels matrice;

	/**
	 * Liste des motifs connus
	 */
	private List<Motif> motifs;

	/**
	 * Colonne actuelle de recherche de motif
	 */
	private int colActuelle = 0;

	/**
	 * Nombre de colonnes vides entre le pr�c�dent motif et l'actuel
	 */
	private int nombreDeColonnesVides = 0;

	/**
	 * Bool�en repr�sentant si on n'a pas encore lu de lettres;
	 */
	private boolean premierPassage = true;

	/**
	 * Vrai si au moins un motif du mot n'a pas �t� reconnu
	 */
	private boolean malReconnu = false;

	/**
	 * Cr�e un chercheur de motifs
	 * 
	 * @param matrice La matrice � fouiller
	 * @param motifs Les motifs connus
	 */
	public ChercheurDeMotifs(MatricePixels matrice, List<Motif> motifs) {
		this.matrice = matrice;
		this.motifs = motifs;
	}

	/**
	 * Permet d'obtenir une cha�ne repr�sentant le texte d�crit par les pixels
	 * 
	 * @param motifs La liste des motifs connus
	 * @return Une chaine avec l'encha�nement des motifs reconnus
	 */
	public String reconnaitre() {
		// Initialisation des param�tres du parcours
		colActuelle = 0;
		premierPassage = true;

		String s = "";

		while (true) {
			// Lecture du motif suivant
			int[] tab = reconnaitreMotif();

			if (tab == null)
				break;

			// Fouille de motif
			String c = trouverMotif(tab);

			// Recherche d'espace
			if (!premierPassage && this.nombreDeColonnesVides >= LARGEUR_ESPACE) {
				c = " " + c;
			} else {
				premierPassage = false;
			}

			s = s + c;
		}

		return s;
	}

	/* =========================
	 * Chercher le motif suivant
	 * ========================= */

	/**
	 * Parcours les colonnes non explor�es. Renvoie le premier motif trouv� sous forme de nombres quand il a trouv� une
	 * colonne non vide
	 * 
	 * @return Le motif trouv� sous forme num�rique
	 */
	private int[] reconnaitreMotif() {
		nombreDeColonnesVides = colActuelle;

		while (colActuelle != matrice.getLongueur()) {
			int firstCross = getFirstCross();

			if (firstCross != -1) {
				nombreDeColonnesVides = colActuelle - nombreDeColonnesVides;
				return cadrer();
			}

			colActuelle++;
		}

		nombreDeColonnesVides = 0;
		return null;
	}

	/**
	 * Permet d'encadrer le motif trouv�, en supposant que l'on connait d�j� la borne de gauche qui est dans colActuelle
	 * 
	 * @return Le motif trouv� sous forme num�rique
	 */
	private int[] cadrer() {
		int xMin = colActuelle;
		int yMin = -1;
		int yMax = -1;

		int firstCross;
		int lastCross;

		while (true) {
			if (colActuelle == matrice.getLongueur())
				break;

			firstCross = getFirstCross();

			if (firstCross == -1)
				break;

			lastCross = getLastCross();

			if (yMin == -1 || yMin > firstCross) {
				yMin = firstCross;
			}

			if (yMax == -1 || yMax < lastCross) {
				yMax = lastCross;
			}

			colActuelle++;
		}

		int xMax = colActuelle - 1;

		return designerMotif(xMin, xMax, yMin, yMax);
	}

	/**
	 * Converti les bornes trouv�es pour un motif en une s�rie de nombres le repr�sentant
	 * 
	 * @param xMin La borne gauche
	 * @param xMax La borne droite
	 * @param yMin La borne haute
	 * @param yMax La borne basse
	 * @return La liste des nombres repr�sentant le motif Avec xi le nombre � la ligne i, si xi mod 2^p = 1, alors le
	 *         pixel (i-1, p) est allum� sur le motif
	 */
	private int[] designerMotif(int xMin, int xMax, int yMin, int yMax) {
		int[] representation = new int[yMax - yMin + 1];

		for (int l = yMin; l <= yMax; l++) {
			int somme = 0;
			int pow = 1;
			for (int c = xMin; c <= xMax; c++) {
				if (matrice.get(c, l)) {
					somme = somme + pow;
				}

				pow = pow * 2;
			}

			representation[l - yMin] = somme;
		}

		return representation;
	}

	/**
	 * Recherche la croix la plus haute sur la colonne
	 * 
	 * @return La position de la premi�re croix en partant du haut sur la ligne colActuelle ou -1 si il n'y en a pas
	 */
	private int getFirstCross() {
		for (int ligne = 0; ligne != matrice.getHauteur(); ligne++) {
			if (matrice.get(colActuelle, ligne)) {
				return ligne;
			}
		}

		return -1;
	}

	/**
	 * Recherche la croix la plus basse sur la colonne
	 * 
	 * @return La position de la premi�re croix en partant du bas sur la ligne colActuelle ou -1 si il n'y en a pas
	 */
	private int getLastCross() {
		for (int ligne = 0; ligne != matrice.getHauteur(); ligne++) {
			if (matrice.get(colActuelle, matrice.getHauteur() - 1 - ligne)) {
				return matrice.getHauteur() - 1 - ligne;
			}
		}

		return -1;
	}

	/**
	 * Cherche le motif donn� dans la base de motifs.
	 * 
	 * Si il n'y est pas, affiche le mot ayant pos� probl�me, le motif ayant pos� probl�me et la ligne de code � ajouter
	 * � SymboleReconnus afin de compl�ter la liste des motifs pr�existants
	 * 
	 * @param tab La repr�sentation du motif
	 * @return La cha�ne repr�sentant le motif
	 */
	private String trouverMotif(int[] tab) {
		for (Motif motif : motifs) {
			if (motif.comparer(tab)) {
				return motif.getSymboleDesigne();
			}
		}

		if (!malReconnu) {
			matrice.afficher();
		}

		malReconnu = true;

		System.out.println("Motif non reconnu :");

		Motif.dessinerUnMotif(tab);

		System.out.print("?");

		for (int valeur : tab) {
			System.out.print(" " + valeur);
		}

		System.out.println();

		motifs.add(new Motif('?', tab));

		return "?";
	}

}
