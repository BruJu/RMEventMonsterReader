package fr.bruju.rmeventreader.actionmakers.composition.actionmaker;

import java.util.Map;
import java.util.Set;

import fr.bruju.rmeventreader.actionmakers.actionner.AutoActionMaker;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.actionmakers.composition.visiteur.implementation.deduction.Deducteur;

/**
 * Cette classe permet d'avoir un point d'entrée unique et simple pour extraire une composition.
 * 
 * @author Bruju
 *
 */
public class Extracteur {

	public Map<Integer, Algorithme> extraireAlgorithmes(Set<Integer> variablesPistees, String chemin) {
		// Mise en place des structures
		ComposeurInitial composeur = new ComposeurInitial(variablesPistees);
		AutoActionMaker autoActionMaker = new AutoActionMaker(composeur, chemin);
		
		// Extraction d'un premier résultat
		autoActionMaker.run();
		Map<Integer, Algorithme> r = composeur.getResultat();
		
		// Simplifications ne se reposant pas sur des connaissances métier
		r.replaceAll((key, algo) -> (Algorithme) new Deducteur().traiter(algo));
		
		return r;
	}
}
