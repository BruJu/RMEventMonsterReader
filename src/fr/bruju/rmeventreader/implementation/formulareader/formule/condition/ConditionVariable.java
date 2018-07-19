package fr.bruju.rmeventreader.implementation.formulareader.formule.condition;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;
import fr.bruju.rmeventreader.implementation.formulareader.formule.Utilitaire;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.simple.ValeurNumerique;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.simple.ValeurVariable;
import fr.bruju.rmeventreader.rmdatabase.Affectation;
import fr.bruju.rmeventreader.rmdatabase.AffectationFlexible;

public class ConditionVariable implements Condition {
	private Valeur gauche;
	private Operator operateur;
	private Valeur droite;

	public ConditionVariable(Valeur gauche, Operator operateur, Valeur droite) {
		this.gauche = gauche;
		this.operateur = operateur;
		this.droite = droite;
	}

	@Override
	public Boolean resoudre(Affectation affectation) {
		Valeur valeurGauche = gauche.evaluationPartielle(affectation);
		Valeur valeurDroite = droite.evaluationPartielle(affectation);

		try {
			int[] evalG = valeurGauche.evaluer();
			int[] evalD = valeurDroite.evaluer();

			boolean[] resultat = testerValuation(operateur, evalG, evalD);

			boolean testMin = resultat[0];
			boolean testMax = resultat[1];

			if (testMin != testMax) {
				return null;
			}

			return testMin;
		} catch (NonEvaluableException | DependantDeStatistiquesEvaluation e) {
			return null;
		}
	}

	public static boolean[] testerValuation(Operator operateur, int[] evalG, int[] evalD) {
		boolean testMin = operateur.test(evalG[0], evalD[0]);
		boolean testMax = operateur.test(evalG[1], evalD[1]);

		return new boolean[] { testMin, testMax };
	}

	public boolean[] tester() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		int[] evalG = gauche.evaluer();
		int[] evalD = droite.evaluer();

		boolean[] resultat = testerValuation(operateur, evalG, evalD);

		return resultat;
	}

	public String getString() {
		return gauche.getString() + " " + Utilitaire.getSymbole(operateur) + " " + droite.getString();
	}

	public Valeur getGauche() {
		return gauche;
	}

	public Valeur getDroite() {
		return droite;
	}

	public Operator getOperator() {
		return operateur;
	}

	@Override
	public Condition revert() {
		return new ConditionVariable(gauche, operateur.revert(), droite);
	}

	@Override
	public Condition evaluationPartielle(Affectation affectation) {
		return new ConditionVariable(gauche.evaluationPartielle(affectation), operateur,
				droite.evaluationPartielle(affectation));
	}

	@Override
	public void modifierAffectation(AffectationFlexible affectation) throws AffectationNonFaisable {
		if (!(droite instanceof ValeurNumerique)) {
			throw new AffectationNonFaisable();
		}

		if (!(gauche instanceof ValeurVariable)) {
			return;

			// throw new AffectationNonFaisable();
		}

		ValeurVariable variable = (ValeurVariable) gauche;
		ValeurNumerique valeur = (ValeurNumerique) droite;

		Integer valeurDansAffectation = affectation.getVariable(variable.getIdVariable());

		if (valeurDansAffectation == null) {
			int valeurReference = valeur.getValue();
			int valeurAMettre;

			switch (operateur) {
			case DIFFERENT:
			case INF:
				valeurAMettre = valeurReference - 1;
				break;
			case IDENTIQUE:
			case INFEGAL:
			case SUPEGAL:
				valeurAMettre = valeurReference;
				break;
			case SUP:
				valeurAMettre = valeurReference + 1;
				break;
			default:
				throw new AffectationNonFaisable();
			}

			affectation.putVariable(variable.getIdVariable(), valeurAMettre);
		} else {
			if (operateur.test(valeurDansAffectation, valeur.getValue())) {
				throw new AffectationNonFaisable();
			}
		}
	}

	@Override
	public Condition integrerCondition(Condition aInclure) {
		// Types identiques
		if (!(aInclure instanceof ConditionVariable)) {
			return this;
		}

		ConditionVariable aInc = (ConditionVariable) aInclure;

		// N'intègre que les conditions de la forme Valeur • Constante 
		if (!(gauche == aInc.gauche && droite instanceof ValeurNumerique && aInc.droite instanceof ValeurNumerique)) {
			return this;
		}

		// N'intègre que les conditions de la forme Valeur • Constante 

		if (aInc.operateur == Operator.IDENTIQUE || operateur == Operator.IDENTIQUE) {
			try {
				boolean res = operateur.test(aInc.droite.evaluerUnique(), droite.evaluerUnique());
				return ConditionFixe.get(res);
			} catch (NonEvaluableException | DependantDeStatistiquesEvaluation e) {
				return this;
			}
		}

		if (aInc.operateur == Operator.DIFFERENT || operateur == Operator.DIFFERENT) {
			return ConditionFixe.VRAI;
		}

		ConditionVariable sousConditionAInclude = aInc.sansEgal();
		ConditionVariable sousConditionthis = sansEgal();

		try {

			if (sousConditionAInclude.operateur == sousConditionthis.operateur) {
				return ConditionFixe.VRAI;
			} else {
				if (sousConditionAInclude.operateur == Operator.INF) {
					return ConditionFixe.get(operateur.test(sousConditionAInclude.droite.evaluer()[1],
							sousConditionthis.droite.evaluer()[0]));
				} else {
					return ConditionFixe.get(operateur.test(sousConditionAInclude.droite.evaluer()[0],
							sousConditionthis.droite.evaluer()[1]));
				}
			}
		} catch (NonEvaluableException | DependantDeStatistiquesEvaluation e) {
			return this;
		}
	}

	private ConditionVariable sansEgal() {
		ValeurNumerique vDroite = (ValeurNumerique) droite;

		if (this.operateur == Operator.INFEGAL) {
			return new ConditionVariable(this.gauche, Operator.INF, new ValeurNumerique(vDroite.evaluer()[1] + 1));
		} else if (this.operateur == Operator.SUPEGAL) {
			return new ConditionVariable(this.gauche, Operator.SUP, new ValeurNumerique(vDroite.evaluer()[0] - 1));
		} else {
			return this;
		}
	}

}
