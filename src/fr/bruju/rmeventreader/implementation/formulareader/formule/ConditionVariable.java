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

	@Override
	public Condition revert() {
		return new ConditionVariable(gauche, operateur.revert(), droite);
	}
	
	@Override
	public int degreDeSimilitude(Condition autre) {
		if (autre == null)
			return 0;
		
		if (!(autre instanceof ConditionVariable))
			return 0;
		
		ConditionVariable autreV = (ConditionVariable) autre;
		
		if (this.getGauche() != autreV.getGauche())
			return 1;
		
		if (this.operateur != autreV.operateur)
			return 2;
		
		return 3;
	}

	@Override
	public String getStringApresAutre(Condition autre) {
		int degre = degreDeSimilitude(autre);
		
		switch (degre) {
		case 3:
			return droite.getString();
		case 2:
			return operateur.name() + " " + droite.getString();
		case 1:
		case 0:
		default:
			return getString();
		}
	}

	@Override
	public Valeur estVariableIdentiqueA() {
		return (operateur == Operator.IDENTIQUE) ? gauche : null;
	}
}
