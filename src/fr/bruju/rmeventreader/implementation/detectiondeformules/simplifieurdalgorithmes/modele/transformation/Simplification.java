package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation.Transformateur;

/**
 * Une simplification transforme un algorithme sans connaître son étiquette
 */
public interface Simplification extends Transformateur {
	/**
	 * Transforme l'algorithme
	 * @param algorithme L'algorithme à transformer
	 * @return L'algorithme transformé
	 */
	public Algorithme simplifier(Algorithme algorithme);

	@Override
	default void accept(Transformateur.Visiteur visiteur) {
		visiteur.visit(this);
	}
}
