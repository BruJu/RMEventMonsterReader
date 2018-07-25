package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.factorisation;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VStatistique;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.utilitaire.Pair;

public class RepresentationVariadique {
	private List<Pair<Valeur, Operator>> facteurs;
	
	public RepresentationVariadique(List<Pair<Valeur, Operator>> facteurs) {
		this.facteurs = facteurs;
	}
	
	public RepresentationVariadique(List<Valeur> facteurs, List<Operator> operateurs, Operator operateurFacteur) {
		this.facteurs = new ArrayList<>();
		
		this.facteurs.add(new Pair<>(facteurs.get(0), operateurFacteur));
		
		for (int i = 1 ; i != facteurs.size() ; i++) {
			this.facteurs.add(new Pair<>(facteurs.get(i), operateurs.get(i-1)));
		}
	}

	/**
	 * 
	 * 
	 * @param secondTerme
	 * @param niveauDOperateur 
	 * @return La factorisation des deux représentations
	 */
	public RepresentationVariadique factoriser(RepresentationVariadique secondTerme, int niveauDOperateur) {
		
		int nivGauche = Factorisation.getNiveau(facteurs.get(0).getRight());
		int nivDroite = Factorisation.getNiveau(secondTerme.facteurs.get(0).getRight());
		
		int niveauMax = Math.max(nivGauche, nivDroite);
		
		if (Math.min(nivGauche, nivDroite) != niveauMax || niveauMax == -1 || niveauDOperateur <= niveauMax) {
			return null;
		}
		
		// Récupération de l'opérateur sur lequel on va factoriser
		Operator operateurDeFactorisation = facteurs.get(0).getRight();
		
		if (Factorisation.getNiveau(operateurDeFactorisation) != niveauMax) {
			operateurDeFactorisation = secondTerme.facteurs.get(0).getRight();
		}
		
		int elementNeutre = operateurDeFactorisation.getNeutre();
		operateurDeFactorisation = Operator.sensConventionnel(operateurDeFactorisation);
		
		List<Pair<Valeur, Operator>> factorisation = new ArrayList<>();
		
		int i = 0;
		while (i != facteurs.size()) {
			Pair<Valeur, Operator> paire = facteurs.get(i);
			
			if (secondTerme.facteurs.remove(paire)) {
				facteurs.remove(paire);
				factorisation.add(paire);
			} else {
				i++;
			}
		}
		
		if (factorisation.isEmpty()) {
			return null;
		}
		
		if (facteurs.isEmpty()) {
			facteurs.add(new Pair<>(new VConstante(elementNeutre), operateurDeFactorisation));
		}
		
		if (secondTerme.facteurs.isEmpty()) {
			secondTerme.facteurs.add(new Pair<>(new VConstante(elementNeutre), operateurDeFactorisation));
		}
		
		return new RepresentationVariadique(factorisation);
	}
	
	public Valeur convertirEnCalcul() {
		Valeur v = facteurs.get(0).getLeft();
		
		for (int i = 1 ; i != facteurs.size(); i++) {
			Pair<Valeur, Operator> facteur = facteurs.get(i);
			
			v = new VCalcul(v, facteur.getRight(), facteur.getLeft());
		}
		
		return v;
	}


	public boolean possedeHP() {
		return this.facteurs.stream().map(p -> p.getLeft()).filter(v -> v instanceof VStatistique)
		.anyMatch(v -> ((VStatistique) v).statistique.nom.equals("HP"));
	}


	public Operator getOperateur() {
		return this.facteurs.get(0).getRight();
	}
}
