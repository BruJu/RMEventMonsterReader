package fr.bruju.rmeventreader.utilitaire;

/**
 * Une pile de booléens où il est rapide de savoir si tous les éléments de la
 * pile sont vrais.
 * 
 * @author Bruju
 */
public class PileDeBooleens {
	private int pile = 0;
	private int sommet = 1;

	/**
	 * Empile le booléen donné
	 * @param booleen Le booléen à empiler
	 */
	public void empiler(boolean booleen) {
		if (!booleen) {
			pile = pile + sommet;
		}

		sommet = sommet * 2;
	}

	/**
	 * Permet de savoir si la pile est vide
	 * @return Vrai si la pile est vode
	 */
	public boolean estVide() {
		return sommet == 1;
	}

	/**
	 * Dépile et renvoie l'élément au sommet
	 * @return Le booléen en sommet de pile
	 */
	public boolean depiler() {
		sommet = sommet / 2;

		if (pile >= sommet) {
			pile -= sommet;
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Permet de savoir si la pile ne contient que des �l�ments vrais
	 * @return Vrai si aucun �l�ment de la pile est faux.
	 */
	public boolean toutAVrai() {
		return pile == 0;
	}

	/**
	 * Inverse le sommet de la pile
	 */
	public void inverseSommet() {
		empiler(!depiler());
	}
}