package fr.bruju.rmeventreader.implementation.formulareader.formule.condition;

import java.util.Iterator;
import java.util.List;

import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;

public class ConditionFixe implements Condition {
	public static final ConditionFixe VRAI = new ConditionFixe(true);
	public static final ConditionFixe FAUX = new ConditionFixe(false);
	
	public static Condition get(boolean res) {
		return res ? VRAI : FAUX;
	}
	
	
	boolean valeur;
	
	
	private ConditionFixe(boolean valeur) {
		this.valeur = valeur;
	}

	@Override
	public String getString() {
		return valeur ? "Vrai" : "Faux";
	}

	@Override
	public Condition revert() {
		return (valeur) ? FAUX : VRAI;
	}

	@Override
	public boolean[] tester() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		return new boolean[] {valeur, valeur};
	}


	// Simplificiation de condition : il n'est pas possible de simplifier cette condition
	
	@Override
	public Condition integrerConditions(List<Condition> conditions) {
		return this;
	}

	@Override
	public Condition integrerConditions(Iterator<Condition> iterator) {
		return this;
	}

	@Override
	public Condition integrerCondition(Condition aInclure) {
		return this;
	}
	
	@Override
	public Boolean fastEval() {
		return valeur;
	}

	@Override
	public int getSimiliHash() {
		return (valeur) ? 71 : 12474865;
	}

	@Override
	public boolean estSimilaire(Condition autreCondition) {
		return autreCondition == this;
	}



}
