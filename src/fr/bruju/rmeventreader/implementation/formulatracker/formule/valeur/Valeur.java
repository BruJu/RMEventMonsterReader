package fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.Composant;

/**
 * Valeur pouvant être évaluer numériquement. Une valeur est contenue dans une variable dans RPG Maker. Dans RPG Maker,
 * cette valeur est bornée de -9999999 à 9999999. Ici elle est bornée selon la représentation d'un int.
 *
 */
public interface Valeur extends Composant {

}
