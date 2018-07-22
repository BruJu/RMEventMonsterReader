package fr.bruju.rmeventreader.implementation.formulareader.formule.condition;

import java.util.Objects;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.simple.ValeurNumerique;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

public class ConditionVariable implements Condition {
	private Valeur gauche;
	private Operator operateur;
	private Valeur droite;

	public ConditionVariable(Valeur gauche, Operator operateur, Valeur droite) {
		this.gauche = gauche;
		this.operateur = operateur;
		this.droite = droite;
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
		try {

			if (aInc.operateur == Operator.IDENTIQUE) {
				boolean res = operateur.test(droite.evaluerUnique(), aInc.droite.evaluerUnique());
				return ConditionFixe.get(res);

			}

			if (operateur == Operator.IDENTIQUE) {
				return ConditionFixe.get(aInc.operateur.test(droite.evaluerUnique(), aInc.droite.evaluerUnique()));
			}

			if (aInc.operateur == Operator.DIFFERENT || operateur == Operator.DIFFERENT) {
				return ConditionFixe.VRAI;
			}

			ConditionVariable sousConditionAInclude = aInc.sansEgal();
			ConditionVariable sousConditionthis = sansEgal();

			if (sousConditionAInclude.operateur == sousConditionthis.operateur) {
				return ConditionFixe.VRAI;
			} else {
				if (sousConditionAInclude.operateur == Operator.INF) {
					return ConditionFixe.get(sousConditionAInclude.operateur.test(
							sousConditionthis.droite.evaluer()[0], sousConditionAInclude.droite.evaluer()[1] - 1

					));
				} else {
					return ConditionFixe.get(sousConditionAInclude.operateur.test(
							sousConditionthis.droite.evaluer()[1], sousConditionAInclude.droite.evaluer()[0] + 1));
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

	
	@Override
	public int getSimiliHash() {
		return Objects.hash("VAR", gauche);
	}
	

	@Override
	public boolean estSimilaire(Condition autreCondition) {
		if (!(autreCondition instanceof ConditionVariable)) {
			return false;
		}
		
		ConditionVariable autre = (ConditionVariable) autreCondition;
		
		return this.gauche.estSimilaire(autre.gauche);
	}

}
