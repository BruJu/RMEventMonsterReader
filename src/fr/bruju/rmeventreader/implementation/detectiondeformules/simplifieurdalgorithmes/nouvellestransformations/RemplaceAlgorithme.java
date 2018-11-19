package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.nouvellestransformations;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.util.table.Table;

import java.util.function.UnaryOperator;

public class RemplaceAlgorithme implements NouveauTransformateur {
    private final UnaryOperator<Algorithme> modifieur;

    public RemplaceAlgorithme(UnaryOperator<Algorithme> modifieur) {
        this.modifieur = modifieur;
    }

    @Override
    public Table appliquer(Table table) {
        table.forEach(enregistrement -> enregistrement.set("Algorithme",
                transformer((Algorithme) enregistrement.get("Algorithme"))));
        return table;
    }

    private Algorithme transformer(Algorithme algorithme) {
        return modifieur.apply(algorithme);
    }
}
