package fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.arbre;

import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.exploitation.Statistique;

public class Resultat {

	public final Statistique stat;
	public final Algorithme algo;

	public Resultat(Statistique stat, Algorithme algo) {
		this.stat = stat;
		this.algo = algo;
	}

	@Override
	public String toString() {
		return stat.toString()+"@"+algo.toString();
	}

	
}
