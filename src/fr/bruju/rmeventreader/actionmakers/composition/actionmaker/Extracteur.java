package fr.bruju.rmeventreader.actionmakers.composition.actionmaker;

import java.util.Map;
import java.util.Set;

import fr.bruju.rmeventreader.actionmakers.actionner.AutoActionMaker;
import fr.bruju.rmeventreader.actionmakers.composition.composant.operation.Affectation;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Constante;
import fr.bruju.rmeventreader.actionmakers.composition.visiteur.implementation.deduction.Deducteur;
import fr.bruju.rmeventreader.implementation.recomposeur.aaaa;

/**
 * Cette classe permet d'avoir un point d'entrée unique et simple pour extraire une composition.
 * 
 * @author Bruju
 *
 */
public class Extracteur {

	public Map<Integer, Algorithme> extraireAlgorithmes(Set<Integer> variablesPistees, String chemin) {
		ComposeurInitial composeur = new ComposeurInitial(variablesPistees);
		AutoActionMaker autoActionMaker = new AutoActionMaker(composeur, chemin);
		
		autoActionMaker.run();
		
		Map<Integer, Algorithme> r = composeur.getResultat();
		
		System.out.println(r.get(514));
		r.replaceAll((cle, algo) -> (Algorithme) new aaaa().traiter(algo));
		System.out.println(r.get(514));

		return r;
	}
}
