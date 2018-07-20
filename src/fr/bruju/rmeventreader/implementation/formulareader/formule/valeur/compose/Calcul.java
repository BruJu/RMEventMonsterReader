package fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.compose;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;
import fr.bruju.rmeventreader.implementation.formulareader.formule.Utilitaire;
import fr.bruju.rmeventreader.implementation.formulareader.formule.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;
import fr.bruju.rmeventreader.rmdatabase.Affectation;

/**
 * Valeur qui est un calcul entre deux valeurs
 * 
 * 
 * L'evaluation minimale et maximale peut donner des résultats faux si l'opérande de droite n'est pas positive constante
 * 
 * @author Bruju
 *
 */
public class Calcul implements Valeur {
	/** Opérande de gauche */
	private Valeur gauche;
	/** Opérateur */
	private Operator operateur;
	/** Opérande de droite */
	private Valeur droite;
	/** Priorité du calcul pour le parenthésage */
	private int priorite;

	/* ============
	 * Constructeur
	 * ============ */

	/**
	 * Construit un calcul à partir de deux valeurs et un opératuer
	 * 
	 * @param gauche Valeur de gauche
	 * @param operateur Un opérateur dans +, -, *, /, %
	 * @param droite Valeur de droite
	 */
	public Calcul(Valeur gauche, Operator operateur, Valeur droite) {
		this.gauche = gauche;
		this.operateur = operateur;
		this.droite = droite;
		this.priorite = setPriorite(operateur);
	}

	/**
	 * Détermine la priorité selon l'opérateur
	 * 
	 * @param operateur L'opérateur
	 * @return La priorité
	 */
	private static int setPriorite(Operator operateur) {
		switch (operateur) {
		case PLUS:
		case MINUS:
			return 2;
		case DIVIDE:
		case MODULO:
		case TIMES:
			return 1;
		default:
			throw new RuntimeException("Opérateur non valide");
		}
	}

	/* ======
	 * Valeur
	 * ====== */

	@Override
	public int getPriorite() {
		return priorite;
	}

	@Override
	public String getString() {
		String gaucheStr = gauche.getString();
		String droiteStr = droite.getString();

		if (gauche.getPriorite() > this.getPriorite()) {
			gaucheStr = "(" + gaucheStr + ")";
		}

		if (droite.getPriorite() > this.getPriorite()) {
			droiteStr = "(" + droiteStr + ")";
		}

		return gaucheStr + " " + Utilitaire.getSymbole(operateur) + " " + droiteStr;
	}
	
	
	/**
	 * Evalue la valeur en fonction d'une fonction d'évaluation des valeurs la composant
	 */
	@Override
	public int[] evaluer() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		int[] operandeGauche;
		int[] operandeDroite;
		
		try {
			operandeGauche = gauche.evaluer();
			
			if (operandeGauche[0] == 0 && operandeGauche[1] == 0 && operateur.estAbsorbantAGauche()) {
				return new int[] {0,0};
			}
			
			operandeDroite = droite.evaluer();
		} catch (NonEvaluableException e) {
			droite.evaluer();
			throw e;
		}
		
		int minmin = operateur.compute(operandeGauche[0], operandeDroite[0]);
		int minmax = operateur.compute(operandeGauche[0], operandeDroite[1]);
		int maxmin = operateur.compute(operandeGauche[1], operandeDroite[0]);
		int maxmax = operateur.compute(operandeGauche[1], operandeDroite[1]);
		
		int vraimin = Math.min(Math.min(minmin, minmax), Math.min(maxmin, maxmax));
		int vraimax = Math.max(Math.max(minmin, minmax), Math.max(maxmin, maxmax));
		
		return new int[] {vraimin, vraimax};
	}
	
	@Override
	public Valeur evaluationPartielle(Affectation affectation) {
		//Calcul calcul = new Calcul(gauche.evaluationPartielle(affectation), operateur, droite.evaluationPartielle(affectation));
		
		//return NewValeur.Numerique(valeurMin, valeurMax);
		return null;
	}
	

	@Override
	public boolean estGarantiePositive() {
		if (operateur == Operator.MINUS) {
			return false;
		}
		
		return gauche.estGarantiePositive() && droite.estGarantiePositive();
	}



	public Valeur getGauche() {
		return this.gauche;
	}

	public Valeur getDroite() {
		return this.droite;
	}

	public Operator getOperateur() {
		return operateur;
	}

	@Override
	public Valeur integrerCondition(List<Condition> aInclure) {
		return new Calcul(gauche.integrerCondition(aInclure), operateur, droite.integrerCondition(aInclure));
	}





}
