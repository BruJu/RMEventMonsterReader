package fr.bruju.rmeventreader.implementation.formulareader.formule;

import fr.bruju.rmeventreader.implementation.formulareader.formule.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;

public interface FonctionEvaluation {
	public int evaluate(Valeur value) throws NonEvaluableException, DependantDeStatistiquesEvaluation;
	public boolean tester(Condition condition)  throws NonEvaluableException, DependantDeStatistiquesEvaluation;
	
	/**
	 * Fonction d'évaluation qui associe la possibilité avec les valeurs les plus petites
	 */
	static final FonctionEvaluation minimum = new FonctionEvaluation() {
		@Override
		public int evaluate(Valeur value) throws NonEvaluableException, DependantDeStatistiquesEvaluation {
			return value.evaluerMin();
		}

		@Override
		public boolean tester(Condition condition) throws NonEvaluableException, DependantDeStatistiquesEvaluation {
			return condition.testerMin();
		}
	};

	/**
	 * Fonction d'évaluation qui associe la possibilité avec les valeurs les plus grandes
	 */
	static final FonctionEvaluation maximum = new FonctionEvaluation() {

		@Override
		public int evaluate(Valeur value) throws NonEvaluableException, DependantDeStatistiquesEvaluation {
			return value.evaluerMax();
		}

		@Override
		public boolean tester(Condition condition) throws NonEvaluableException, DependantDeStatistiquesEvaluation {
			return condition.testerMax();
		}
	};
}
