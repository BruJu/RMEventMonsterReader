package fr.bruju.rmeventreader.implementation.formulatracker.simplification.factoriseur;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.Valeur;
import fr.bruju.rmeventreader.utilitaire.Pair;

public class RepresentationVariadique {
	private List<Pair<Valeur, Operator>> facteurs;
	private int elementNeutre;
	
	public RepresentationVariadique(List<Pair<Valeur, Operator>> facteurs, int elementNeutre) {
		this.facteurs = facteurs;
		this.elementNeutre = elementNeutre;
	}
	
	
	/**
	 * 
	 * 
	 * @param secondTerme
	 * @return La factorisation des deux représentations
	 */
	public RepresentationVariadique factoriser(RepresentationVariadique secondTerme) {
		List<Pair<Valeur, Operator>> mesF = facteurs;
		
		List<Pair<Valeur, Operator>> mesNouveauxF = new ArrayList<>();
		List<Pair<Valeur, Operator>> sesF = secondTerme.facteurs;
		List<Pair<Valeur, Operator>> factorisation = new ArrayList<>();
		
		for (Pair<Valeur, Operator> paire : mesF) {
			if (sesF.contains(paire)) {
				sesF.remove(paire);
				factorisation.add(paire);
			} else {
				mesNouveauxF.add(paire);
			}
		}
		
		facteurs = mesNouveauxF;
		
		return new RepresentationVariadique(factorisation, this.elementNeutre);
	}
	
	public Valeur convertirEnCalcul() {
		if (facteurs.isEmpty()) {
			return new VConstante(elementNeutre);
		}
		
		Valeur v = facteurs.get(0).getLeft();
		
		for (int i = 1 ; i != facteurs.size(); i++) {
			Pair<Valeur, Operator> facteur = facteurs.get(i);
			
			v = new VCalcul(v, facteur.getRight(), facteur.getLeft());
		}
		
		return v;
	}


	public boolean isEmpty() {
		return facteurs.isEmpty();
	}
}
