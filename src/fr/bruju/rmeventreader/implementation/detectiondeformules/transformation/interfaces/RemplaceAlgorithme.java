package fr.bruju.rmeventreader.implementation.detectiondeformules.transformation.interfaces;

import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.Algorithme;
import fr.bruju.util.table.Table;

/**
 * Une opération qui modifie l'algorithme de chaque enregistrement
 */
public abstract class RemplaceAlgorithme implements TransformationDeTable {
	/** Nom du champ contenant les algorithmes */
	public static String CHAMP_ALGORITHME = "Algorithme";

    @Override
    public final Table appliquer(Table table) {
        table.forEach(enregistrement -> enregistrement.set(CHAMP_ALGORITHME,
				simplifier(enregistrement.get(CHAMP_ALGORITHME))));
        return table;
    }

	/**
	 * Fonction qui transforme l'algorithme de base
	 * @param base L'algorithme de base
	 * @return L'algorithme transformé
	 */
	protected abstract Algorithme simplifier(Algorithme base);
}
