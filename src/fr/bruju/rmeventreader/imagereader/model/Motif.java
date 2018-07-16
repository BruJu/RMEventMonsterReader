package fr.bruju.rmeventreader.imagereader.model;

/**
 * Motif preexistant
 */
public class Motif {
	/**
	 * Chaîne représentée par le motif
	 */
	private String lettre;

	/**
	 * Représentation numérique du motif
	 */
	private int[] composition;

	/**
	 * Crée un motif préconnu
	 * 
	 * @param chaine Chaîne représentée par le motif
	 * @param composition Représentation numérique du motif
	 */
	public Motif(String chaine, int[] composition) {
		this.lettre = chaine;
		this.composition = composition;
	}

	/**
	 * Crée un motif préconnu
	 * 
	 * @param lettre Lettre représentée par le motif
	 * @param composition Représentation numérique du motif
	 */
	public Motif(char lettre, int[] composition) {
		this.lettre = Character.toString(lettre);
		this.composition = composition;
	}

	/**
	 * Permet de savoir si ce motif et le motif donné sont identiques
	 * 
	 * @param candidat Le motif dont on souhaite savoir si il est égal à ce motif
	 * @return Vrai si candidat et le motif représenté sont identiques
	 */
	public boolean comparer(int[] candidat) {
		if (composition.length != candidat.length)
			return false;

		for (int i = 0; i != composition.length; i++) {
			if (candidat[i] != composition[i])
				return false;
		}

		return true;
	}

	/**
	 * Renvoie la chaîne représentant le motif
	 * 
	 * @return La chaîne représentant le motif
	 */
	public String getSymboleDesigne() {
		return lettre;
	}

	/**
	 * Dessine le motif donné sous forme numérique
	 * 
	 * @param tab Motif sous forme numérique
	 */
	public static void dessinerUnMotif(int[] tab) {
		for (int valeur : tab) {
			dessinerUneLigneDeMotif(valeur);
			System.out.println();
		}
	}

	/**
	 * Dessine une ligne de motif à partir du nombre donné
	 * 
	 * @param valeur Le nombre représentant la ligne
	 */
	private static void dessinerUneLigneDeMotif(int valeur) {
		while (valeur != 0) {
			if (valeur % 2 == 1) {
				System.out.print("x");
			} else {
				System.out.print(" ");
			}

			valeur = valeur / 2;
		}
	}
}