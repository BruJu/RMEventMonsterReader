package fr.bruju.rmeventreader.implementation.recomposeur.arbre;

import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.Statistique;

public class Resultat extends Algorithme {

	public final Statistique stat;
	public final Algorithme algo;

	public Resultat(Statistique stat, Algorithme algo) {
		this.stat = stat;
		this.algo = algo;
	}

}
