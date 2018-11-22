package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.nouvellestransformations;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.util.table.Table;


public abstract class RemplaceAlgorithme implements NouveauTransformateur {
	public static String CHAMP_ALGORITHME = "Algorithme";

    @Override
    public final Table appliquer(Table table) {
        table.forEach(enregistrement -> enregistrement.set(CHAMP_ALGORITHME,
				simplifier(enregistrement.<Algorithme>get(CHAMP_ALGORITHME))));
        return table;
    }

	/**
	 * Fonction qui transforme l'algorithme de base
	 * @param base
	 * @return
	 */
	protected abstract Algorithme simplifier(Algorithme base);
}
