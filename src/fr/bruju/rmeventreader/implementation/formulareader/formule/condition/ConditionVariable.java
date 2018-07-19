package fr.bruju.rmeventreader.implementation.formulareader.formule.condition;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;
import fr.bruju.rmeventreader.implementation.formulareader.formule.Utilitaire;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;
import fr.bruju.rmeventreader.rmdatabase.Affectation;

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
		
		return new boolean[] {testMin, testMax};
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
