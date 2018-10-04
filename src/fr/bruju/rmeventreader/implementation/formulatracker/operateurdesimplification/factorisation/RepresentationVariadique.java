package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.factorisation;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VStatistique;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.rmdechiffreur.modele.OpMathematique;
import fr.bruju.rmeventreader.utilitaire.Pair;

// TODO : documenter cette classe / la retravailler
// WIP avec E_CalculVariadique

/*
 * Il y a des questions à se poser sur si des composants étendus ou un travail réalisé sur le "vif" est le plus
 * pertinent.
 * Les expériences montrent que la manipulation des calculs variadiques est assez lourde et lente.
 * 
 * Si dans ce projet, les considérations de performance sont très peu présentes, dans la mesure où il faut au moins une
 * minute pour exécuter l'ancienne version de E_CalculVariadique, les questions de performances se posent.
 * 
 * L'enjeu serait de trouver une représentation à la fois simple et un minimum performante pour régler ce problème,
 * sachant que dans l'application de ce programme, on peut se permettre d'avoir des hypothèses fortes sur le jeu de
 * données.
 * 
 * Dans la mesure où l'implémentation de cette classe est amenée à changée radicalement ou à être supprimée (intégration
 * dans E_CalculVariadique par exemple), la documentation de cette classe n'est pas faite.
 */

/**
 * Représentation variadique d'une série de VCalcul partageant le même opérateur
 * 
 * @author Bruju
 *
 */
public class RepresentationVariadique {
	private List<Pair<Valeur, OpMathematique>> facteurs;
	
	private OpMathematique preOperator;

	public RepresentationVariadique(List<Pair<Valeur, OpMathematique>> facteurs) {
		this.facteurs = facteurs;
	}

	public RepresentationVariadique(List<Valeur> facteurs, List<OpMathematique> operateurs, OpMathematique operateurFacteur) {
		this.facteurs = new ArrayList<>();

		this.facteurs.add(new Pair<>(facteurs.get(0), operateurFacteur));

		for (int i = 1; i != facteurs.size(); i++) {
			this.facteurs.add(new Pair<>(facteurs.get(i), operateurs.get(i - 1)));
		}
	}

	public Valeur convertirEnCalcul() {
		Valeur v = facteurs.get(0).getLeft();

		for (int i = 1; i != facteurs.size(); i++) {
			Pair<Valeur, OpMathematique> facteur = facteurs.get(i);

			v = new VCalcul(v, facteur.getRight(), facteur.getLeft());
		}

		return v;
	}

	public boolean possedeHP() {
		return this.facteurs.stream().map(p -> p.getLeft()).filter(v -> v instanceof VStatistique)
				.anyMatch(v -> ((VStatistique) v).statistique.nom.equals("HP"));
	}

	public OpMathematique getOperateur() {
		return this.facteurs.get(0).getRight();
	}

	public RepresentationVariadique factoriserGauche(RepresentationVariadique rd) {
		List<Pair<Valeur, OpMathematique>> factorisation = new ArrayList<>();
		
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
			rg.facteurs.add(0, new Pair<>(new VConstante(elementNeutre), OpMathematique.sensConventionnel(f.facteurs.get(0).getRight())));
			rd.facteurs.add(0, new Pair<>(new VConstante(elementNeutre), OpMathematique.sensConventionnel(f.facteurs.get(0).getRight())));

			
		}
		
		if (rg.facteurs.isEmpty()) {
			rg.facteurs.add(0, new Pair<>(new VConstante(elementNeutre), rd.facteurs.get(0).getRight()));
		}
		if (rd.facteurs.isEmpty()) {
			rd.facteurs.add(0, new Pair<>(new VConstante(elementNeutre), rg.facteurs.get(0).getRight()));
		}
		
		if (rg.facteurs.get(0).getRight() == rg.facteurs.get(0).getRight()) {
			
		} else {
			if (rg.facteurs.get(0).getRight() != OpMathematique.sensConventionnel(rg.facteurs.get(0).getRight())) {
				rg.facteurs.add(0, new Pair<>(new VConstante(elementNeutre), rd.facteurs.get(0).getRight()));
			} else {
				rd.facteurs.add(0, new Pair<>(new VConstante(elementNeutre), rg.facteurs.get(0).getRight()));
			}
		} 
		f.preOperator = rg.facteurs.get(0).getRight();
	}

	public OpMathematique getPreOperateur() {
		return this.preOperator;
	}
}
