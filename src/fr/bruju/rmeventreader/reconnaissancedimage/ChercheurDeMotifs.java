package fr.bruju.rmeventreader.reconnaissancedimage;

import java.util.List;

/**
 * Cette classe recherche des motifs dans une matrice de pixels
 */
class ChercheurDeMotifs {
	/** Nombre de colonnes vides consécutives à lire pour afficher un espace */
	private static int LARGEUR_ESPACE = 4;

	/** Matrice de pixels à fouiller */
	private MatricePixels matrice;

	/** Liste des motifs connus */
	private List<Motif> motifs;
	/** Liste des motifs inconnus */
	private List<Motif> motifsInconnus;

	
	/** Colonne actuelle de recherche de motif */
	private int colActuelle = 0;

	/** Nombre de colonnes vides entre le précédent motif et l'actuel */
	private int nombreDeColonnesVides = 0;

	/**
	 * Crée un chercheur de motifs
	 * 
	 * @param matrice La matrice à fouiller
	 * @param motifs Les motifs connus
	 */
	public ChercheurDeMotifs(MatricePixels matrice, List<Motif> motifs, List<Motif> motifsInconnus) {
		this.matrice = matrice;
		this.motifs = motifs;
		this.motifsInconnus = motifsInconnus;
	}

	/**
	 * Permet d'obtenir une chaîne représentant le texte décrit par les pixels
	 * 
	 * @param motifs La liste des motifs connus
	 * @return Une chaine avec l'enchaînement des motifs reconnus
	 */
	public String reconnaitre() {
		// Initialisation des paramètres du parcours
		colActuelle = 0;
		StringBuilder chaineConstruite = new StringBuilder();
		
		boolean unCaractereInconnu = false;
		int[] compositionDuMotif;
		
		while ((compositionDuMotif = chercherProchainMotif()) != null) {
			String c = identifierMotif(compositionDuMotif);
			
			if (c == null) {
				unCaractereInconnu = true;
			} else {
				if (nombreDeColonnesVides >= LARGEUR_ESPACE) {
					chaineConstruite.append(" ");
				}

				chaineConstruite.append(c);
			}
		}

		return unCaractereInconnu ? null : chaineConstruite.toString().trim();
	}

	/* =========================
	 * Chercher le motif suivant
	 * ========================= */

	/**
	 * Parcours les colonnes non explorées. Renvoie le premier motif trouvé sous forme de nombres quand il a trouvé une
	 * colonne non vide
	 * 
	 * @return Le motif trouvé sous forme numérique
	 */
	private int[] chercherProchainMotif() {
		nombreDeColonnesVides = 0;
		int[] valeurs = new int[matrice.hauteur];
		int multiplicateur = 1;
		
		while (colActuelle != matrice.longueur) {
			boolean colonneVide = true;
			
			for (int ligne = 0 ; ligne != matrice.hauteur ; ligne++) {
				if (matrice.estAllume(colActuelle, ligne)) {
					valeurs[ligne] += multiplicateur;
					colonneVide = false;
				}
			}
			
			if (!colonneVide) { // Colonne non vide
				multiplicateur *= 2;
			} else if (multiplicateur == 1) { // Colonne vide (première ou suivant une autre colonne vide)
				nombreDeColonnesVides++;
			} else { // Colonne vide après une colonne non vide
				break;
			}
			
			colActuelle++;
		}
		
		return (multiplicateur == 1) ? null : recadrer(valeurs);
	}
	
	/**
	 * Donne un nouveau tableau d'entier identiques à celui donné en paramètres, mais dont on a enlevé les cases
	 * contenant que des 0 au début du tableau et à la fin du tableau (un peu comme si on avait une chaîne, et que ces
	 * 0 représentaient des espaces, et qu'on faisait un trim).
	 */
	private int[] recadrer(int[] tableau) {
		int xMin, xMax;
		
		for (xMin = 0 ; tableau[xMin] == 0 ; xMin++);
		for (xMax = tableau.length - 1 ; tableau[xMax] == 0 ; xMax--);
		
		int[] nouveauTableau = new int[xMax - xMin + 1];
		
		for (int i = 0 ; i != nouveauTableau.length ; i++) {
			nouveauTableau[i] = tableau[xMin + i];
		}
		
		return nouveauTableau;
	}
	
	/**
	 * Cherche le motif donné dans la base de motifs.
	 * 
	 * Si il n'y est pas, affiche le mot ayant posé problème, le motif ayant posé problème et la ligne de code à ajouter
	 * à SymboleReconnus afin de compléter la liste des motifs préexistants
	 * 
	 * @param tab La représentation du motif
	 * @return La chaîne représentant le motif
	 */
	private String identifierMotif(int[] tab) {
		for (Motif motif : motifs) {
			if (motif.comparer(tab)) {
				return motif.getSymboleDesigne();
			}
		}
		
		Motif motifNonReconnu = new Motif(tab);
		motifs.add(motifNonReconnu);
		motifsInconnus.add(motifNonReconnu);

		return null;
	}
}
