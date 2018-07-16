package fr.bruju.rmeventreader.imagereader.model;

/**
 * Motif preexistant
 */
public class Motif {
	/**
	 * Cha�ne repr�sent�e par le motif
	 */
	private String lettre;

	/**
	 * Repr�sentation num�rique du motif
	 */
	private int[] composition;

	/**
	 * Cr�e un motif pr�connu
	 * 
	 * @param chaine Cha�ne repr�sent�e par le motif
	 * @param composition Repr�sentation num�rique du motif
	 */
	public Motif(String chaine, int[] composition) {
		this.lettre = chaine;
		this.composition = composition;
	}

	/**
	 * Cr�e un motif pr�connu
	 * 
	 * @param lettre Lettre repr�sent�e par le motif
	 * @param composition Repr�sentation num�rique du motif
	 */
	public Motif(char lettre, int[] composition) {
		this.lettre = Character.toString(lettre);
		this.composition = composition;
	}

	/**
	 * Permet de savoir si ce motif et le motif donn� sont identiques
	 * 
	 * @param candidat Le motif dont on souhaite savoir si il est �gal � ce motif
	 * @return Vrai si candidat et le motif repr�sent� sont identiques
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
	 * Renvoie la cha�ne repr�sentant le motif
	 * 
	 * @return La cha�ne repr�sentant le motif
	 */
	public String getSymboleDesigne() {
		return lettre;
	}

	/**
	 * Dessine le motif donn� sous forme num�rique
	 * 
	 * @param tab Motif sous forme num�rique
	 */
	public static void dessinerUnMotif(int[] tab) {
		for (int valeur : tab) {
			dessinerUneLigneDeMotif(valeur);
			System.out.println();
		}
	}

	/**
	 * Dessine une ligne de motif � partir du nombre donn�
	 * 
	 * @param valeur Le nombre repr�sentant la ligne
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