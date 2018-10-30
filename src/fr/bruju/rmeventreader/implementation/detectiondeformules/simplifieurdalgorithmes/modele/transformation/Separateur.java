package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.AlgorithmeEtiquete;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation.Transformateur;

import java.util.function.Consumer;

/**
 * Un séparateur lit chaque algorithme et ajoute à la nouvelle liste ses résultats. Il peut produire 0, 1 ou plusieurs
 * algorithmes à partir d'un même algorithme.
 */
public interface Separateur extends Transformateur {
	/**
	 * Effectue un traitement sur l'élément à séparer, et ajoute les éventuels résultats à la liste en appelant la
	 * fonction d'ajout
	 * @param fonctionDAjout La fonction appelée pour chaque algorithme à considérer lors des prochaines étapes
	 * @param elementASeparer L'algorithme initial
	 */
	public void separer(Consumer<AlgorithmeEtiquete> fonctionDAjout, AlgorithmeEtiquete elementASeparer);

	@Override
	default void accept(Transformateur.Visiteur visiteur) {
		visiteur.visit(this);
	}
}
