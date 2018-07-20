package fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.compose;

import java.util.List;
import java.util.function.UnaryOperator;

import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;
import fr.bruju.rmeventreader.implementation.formulareader.formule.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulareader.formule.condition.ConditionFixe;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;

public class ValeurTernaire implements Valeur {
	private Condition condition;
	private Valeur siVrai;
	private Valeur siFaux;

	public ValeurTernaire(Condition condition, Valeur siVrai, Valeur siFaux) {
		this.condition = condition;
		this.siFaux = siFaux;
		this.siVrai = siVrai;
	}

	@Override
	public int[] evaluer() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		boolean[] conditions = condition.tester();

		boolean conditionMin = conditions[0];
		boolean conditionMax = conditions[1];

		int[] siVraiRes = null;
		int[] siFauxRes = null;

		if (conditionMin || conditionMax) {
			siVraiRes = siVrai.evaluer();
		}

		if (!conditionMin || !conditionMax) {
			siFauxRes = siFaux.evaluer();
		}

		int valeurMin = conditionMin ? siVraiRes[0] : siFauxRes[0];
		int valeurMax = conditionMin ? siVraiRes[1] : siFauxRes[1];

		return new int[] { valeurMin, valeurMax };
	}

	@Override
	public int getPriorite() {
		return 0;
	}

	@Override
	public String getString() {
		return "[(" + condition.getString() + ") ? " + siVrai.getString() + " : " + siFaux.getString() + "]";
	}

	// Ces implémentations ne sont pas assez précises (mais correctes dans la msure où ces méthodes
	// garantissent que si vrai est renvoyé, on est sur de la réponse, mais que renvoyer faux
	// signifie "On ne sait pas")

	@Override
	public boolean estGarantiePositive() {
		return siVrai.estGarantiePositive() && siFaux.estGarantiePositive();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((condition == null) ? 0 : condition.hashCode());
		result = prime * result + ((siFaux == null) ? 0 : siFaux.hashCode());
		result = prime * result + ((siVrai == null) ? 0 : siVrai.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ValeurTernaire other = (ValeurTernaire) obj;
		if (condition == null) {
			if (other.condition != null)
				return false;
		} else if (!condition.equals(other.condition))
			return false;
		if (siFaux == null) {
			if (other.siFaux != null)
				return false;
		} else if (!siFaux.equals(other.siFaux))
			return false;
		if (siVrai == null) {
			if (other.siVrai != null)
				return false;
		} else if (!siVrai.equals(other.siVrai))
			return false;
		return true;
	}


	@Override
	public Valeur integrerCondition(List<Condition> aInclure) {
		Condition incluse = condition.integrerConditions(aInclure);
		
		return new ValeurTernaire(incluse, siVrai, siFaux).deleguerTraitement(v -> v.integrerCondition(aInclure));
	}

	
	@Override
	public Valeur deleguerTraitement(UnaryOperator<Valeur> conversion) {
		if (this.condition == ConditionFixe.VRAI) {
			return conversion.apply(siVrai);
		} else if (this.condition == ConditionFixe.FAUX) {
			return conversion.apply(siFaux);
		} else {
			Condition c = this.condition;
			Valeur v = conversion.apply(siVrai);
			Valeur f = conversion.apply(siFaux);
			
			if (c == null || v == null || f == null) {
				return null;
			}
			
			return new ValeurTernaire(c, v, f);
		}
	}
}
