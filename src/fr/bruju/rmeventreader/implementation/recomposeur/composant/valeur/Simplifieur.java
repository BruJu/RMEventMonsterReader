package fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.ComposantVariadique;
public class Simplifieur {
	ValeurVariadique valeurVariadique;

	List<ComposantVariadique> nouveauxComposants;
	Integer valeurActuelle;

	boolean aChange;

	public Simplifieur(ValeurVariadique valeurVariadique) {
		this.valeurVariadique = valeurVariadique;

		lancerLeProcessus();
	}

	private void lancerLeProcessus() {
		List<ComposantVariadique> composants = valeurVariadique.composants;

		nouveauxComposants = new ArrayList<>(composants.size());
		valeurActuelle = null;
		aChange = false;

		composants.forEach(composant -> composant.cumuler(nouveauxComposants));
	}

	public ValeurVariadique get() {
		return null;
	}
}
