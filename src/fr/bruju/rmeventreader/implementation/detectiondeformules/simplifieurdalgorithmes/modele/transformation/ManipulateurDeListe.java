package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.AlgorithmeEtiquete;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation.Transformateur;

import java.util.List;

/**
 * Un transformateur qui agit sur la globalité de la liste d'algorithmes. Le transformateur peut potentiellement
 * modifier la liste d'origine ou en créer une nouvelle.
 */
public interface ManipulateurDeListe extends Transformateur {
	@Override
	default void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}

	/**
	 * Modifie la liste et donne la nouvelle liste à considérer. L'ancienne liste peut être modifiée.
	 * @param liste La liste d'origine
	 * @return La nouvelle liste
	 */
	public List<AlgorithmeEtiquete> manipuler(List<AlgorithmeEtiquete> liste);
}
