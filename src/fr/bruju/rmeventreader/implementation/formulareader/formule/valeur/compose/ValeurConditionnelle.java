package fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.compose;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;
import fr.bruju.rmeventreader.implementation.formulareader.formule.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulareader.formule.condition.ConditionVariableGroupees;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.simple.ValeurNumerique;
import fr.bruju.rmeventreader.rmdatabase.Affectation;
import fr.bruju.rmeventreader.utilitaire.Pair;

public class ValeurConditionnelle implements Valeur {
	private List<Pair<Condition, Valeur>> valeursConditionnelles;
	private int elementNeutre;

	public ValeurConditionnelle(Condition condition, Valeur valeur, int elementNeutre) {
		valeursConditionnelles = new ArrayList<>();
		valeursConditionnelles.add(new Pair<>(condition, valeur));
		this.elementNeutre = elementNeutre;
	}

	public ValeurConditionnelle(ValeurConditionnelle gauche, ValeurConditionnelle droite) {
		valeursConditionnelles = new ArrayList<>();
		this.elementNeutre = droite.elementNeutre;

		valeursConditionnelles.addAll(gauche.valeursConditionnelles);
		valeursConditionnelles.addAll(droite.valeursConditionnelles);
	}

	public ValeurConditionnelle(List<Pair<Condition, Valeur>> liste, int elementNeutre) {
		this.valeursConditionnelles = liste;
		this.elementNeutre = elementNeutre;
	}

	@Override
	public int getPriorite() {
		return 0;
	}

	@Override
	public String getString() {
		List<Condition> conditions = valeursConditionnelles.stream().map(paire -> paire.getLeft())
				.collect(Collectors.toList());

		if (ConditionVariableGroupees.affichageEnsemblistePossible(conditions)) {
			return ConditionVariableGroupees.getStringEnsembliste(valeursConditionnelles);
		} else {
			return getStringNormal();
		}
	}

	private String getStringNormal() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");

		for (Pair<Condition, Valeur> paireCondValeur : valeursConditionnelles) {
			Condition cond = paireCondValeur.getLeft();
			Valeur valeur = paireCondValeur.getRight();

			if (sb.toString().length() != 1) {
				sb.append(" | ");
			}

			sb.append(cond.getString()).append(" -> ").append(valeur.getString());
		}

		sb.append("}");

		return sb.toString();
	}

	@Override
	public boolean estGarantiePositive() {
		if (this.elementNeutre <= 0)
			return false;

		for (Pair<Condition, Valeur> paireCondValeur : valeursConditionnelles) {
			Valeur valeur = paireCondValeur.getRight();

			if (!valeur.estGarantiePositive()) {
				return false;
			}
		}

		return true;
	}

	@Override
	public Valeur evaluationPartielle(Affectation affectation) {
		List<Pair<Condition, Valeur>> liste = null;

		for (Pair<Condition, Valeur> paireCondValeur : valeursConditionnelles) {
			if (liste != null) {
				liste.add(paireCondValeur);
			} else {
				Condition cond = paireCondValeur.getLeft();

				Boolean resultat = cond.resoudre(affectation);

				if (resultat == null) {
					liste = new ArrayList<>();
					liste.add(paireCondValeur);
				} else if (resultat == Boolean.TRUE) {
					return paireCondValeur.getRight().evaluationPartielle(affectation);
				}
			}
		}

		if (liste == null) {
			return new ValeurNumerique(elementNeutre);
		}

		return new ValeurConditionnelle(liste, this.elementNeutre);
	}

	@Override
	public int[] evaluer() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		int valeurMin = -1;
		int valeurMax = -1;
		boolean aUneValeur = false;

		for (Pair<Condition, Valeur> paireCondValeur : valeursConditionnelles) {
			Condition cond = paireCondValeur.getLeft();
			Valeur valeur = paireCondValeur.getRight();

			if (cond.tester()[1]) {
				int[] resultat = valeur.evaluer();
				if (!aUneValeur) {
					valeurMin = resultat[0];
					valeurMax = resultat[1];
					aUneValeur = true;
				} else {
					valeurMin = Math.min(valeurMin, resultat[0]);
					valeurMax = Math.max(valeurMin, resultat[1]);
				}
			}
		}

		if (aUneValeur) {
			return new int[] { valeurMin, valeurMax };
		} else {
			return new int[] { elementNeutre, elementNeutre };
		}
	}
}
