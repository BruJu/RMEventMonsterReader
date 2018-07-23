package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.filtres.hppositif;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.ConstructeurDeComposantR;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurRetourneur;

/**
 * Cette classe a pour but de filtrer toutes les conditions du type HP - [VALEUR] < 1 en disant qu'elles ne sont
 * jamais vérifiées.
 * A l'inverse, les conditions du type HP - [VALEUR] > 0 sont toujours vérifiées
 * @author Bruju
 *
 */
public class FiltreHPPositifs extends ConstructeurDeComposantR {

}
