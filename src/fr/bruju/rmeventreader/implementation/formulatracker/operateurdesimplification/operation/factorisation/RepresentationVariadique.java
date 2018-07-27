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
	
	private Operator preOperator;
	//private Operator postOperator;

	public RepresentationVariadique(List<Pair<Valeur, Operator>> facteurs) {
		this.facteurs = facteurs;
	}

	public RepresentationVariadique(List<Valeur> facteurs, List<Operator> operateurs, Operator operateurFacteur) {
		this.facteurs = new ArrayList<>();

		this.facteurs.add(new Pair<>(facteurs.get(0), operateurFacteur));

		for (int i = 1; i != facteurs.size(); i++) {
			this.facteurs.add(new Pair<>(facteurs.get(i), operateurs.get(i - 1)));
		}
	}

	public Valeur convertirEnCalcul() {
		Valeur v = facteurs.get(0).getLeft();

		for (int i = 1; i != facteurs.size(); i++) {
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

	public RepresentationVariadique factoriserGauche(RepresentationVariadique rd) {
		List<Pair<Valeur, Operator>> factorisation = new ArrayList<>();
		
		while (0 != facteurs.size() && 0 != rd.facteurs.size()) {
			if (facteurs.get(0).equals(rd.facteurs.get(0))) {
				factorisation.add(facteurs.get(0));
				
				facteurs.remove(0);
				rd.facteurs.remove(0);
			} else {
				break;
			}
		}
		
		if (factorisation.isEmpty()) {
			return null;
		}
		
		
		RepresentationVariadique f = new RepresentationVariadique(factorisation);
		
		finaliserG(this, rd, f);
		
		return f;
	}

	private static void finaliserG(RepresentationVariadique rg, RepresentationVariadique rd,
			RepresentationVariadique f) {
		int elementNeutre = f.facteurs.get(0).getRight().getNeutre();
		
		if (rg.facteurs.isEmpty() && rd.facteurs.isEmpty()) {
			rg.facteurs.add(0, new Pair<>(new VConstante(elementNeutre), Operator.sensConventionnel(f.facteurs.get(0).getRight())));
			rd.facteurs.add(0, new Pair<>(new VConstante(elementNeutre), Operator.sensConventionnel(f.facteurs.get(0).getRight())));

			
		}
		
		if (rg.facteurs.isEmpty()) {
			rg.facteurs.add(0, new Pair<>(new VConstante(elementNeutre), rd.facteurs.get(0).getRight()));
		}
		if (rd.facteurs.isEmpty()) {
			rd.facteurs.add(0, new Pair<>(new VConstante(elementNeutre), rg.facteurs.get(0).getRight()));
		}
		
		if (rg.facteurs.get(0).getRight() == rg.facteurs.get(0).getRight()) {
			//f.preOperator = rg.facteurs.get(0).getRight();
		} else {
			if (rg.facteurs.get(0).getRight() != Operator.sensConventionnel(rg.facteurs.get(0).getRight())) {
				rg.facteurs.add(0, new Pair<>(new VConstante(elementNeutre), rd.facteurs.get(0).getRight()));
			} else {
				rd.facteurs.add(0, new Pair<>(new VConstante(elementNeutre), rg.facteurs.get(0).getRight()));
			}
		} 
		f.preOperator = rg.facteurs.get(0).getRight();
	}

	public RepresentationVariadique factoriserDroite(RepresentationVariadique rd) {
		return null;
	}

	public Operator getPreOperateur() {
		return this.preOperator;
	}
	public Operator getPostOperateur() {
		return null;
	}
}