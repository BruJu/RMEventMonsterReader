package fr.bruju.rmeventreader.implementation.formulareader.formule;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;

public class ConditionVariable implements Condition {
	private Valeur gauche;
	private Operator operateur;
	private Valeur droite;
	
	public ConditionVariable(Valeur gauche, Operator operateur, Valeur droite) {
		this.gauche = gauche;
		this.operateur = operateur;
		this.droite = droite;
	}
	
	public boolean tester() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		return operateur.test(gauche.evaluer(), droite.evaluer());
	}
	
	public boolean testerMax() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		return operateur.test(gauche.evaluerMax(), droite.evaluerMax());
	}
	
	public boolean testerMin() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		return operateur.test(gauche.evaluerMin(), droite.evaluerMin());
	}
	
	public String getString() {
		return gauche.getString() + " " + operateur.name() + " " + droite.getString();
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
}
