package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.AlgorithmeEtiquete;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.personnage.BaseDePersonnages;

/**
 * Un unificateur prendre deux algorithmes étiquetés et tente de les fusionner. Renvoie la fusion, ou null en cas
 * d'échec
 */
public interface Unificateur extends Transformateur {
	/**
	 * Tente d'unifier les deux algorithmse
	 * @param algo1 Le premier algorithme
	 * @param algo2 Le second algorithme
	 * @return La fusion des deux algorithmes, ou null si impossible
	 */
	public AlgorithmeEtiquete unifier(AlgorithmeEtiquete algo1, AlgorithmeEtiquete algo2, BaseDePersonnages personnages);

	@Override
	default void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}
}
