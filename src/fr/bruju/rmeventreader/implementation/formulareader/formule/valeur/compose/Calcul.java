package fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.compose;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.FonctionEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NewValeur;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;
import fr.bruju.rmeventreader.implementation.formulareader.formule.Utilitaire;
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
	
	// TODO : Corriger les évaluations pour renvoyer des résultats corrects

	@Override
	public int evaluerMin() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		if (operateur == Operator.MINUS) {
			// Il faut enlever le plus possible au maximum
			return evaluer(FonctionEvaluation.minimum, FonctionEvaluation.maximum);
		} else {
			return evaluer(FonctionEvaluation.minimum, FonctionEvaluation.minimum);
		}
	}

	@Override
	public int evaluerMax() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		if (operateur == Operator.MINUS) {
			// Il faut enlever le moins possible au maximum
			return evaluer(FonctionEvaluation.maximum, FonctionEvaluation.minimum);
		} else {
			return evaluer(FonctionEvaluation.maximum, FonctionEvaluation.maximum);
		}
	}
	
	/**
	 * Evalue la valeur en fonction d'une fonction d'évaluation des valeurs la composant
	 */
	public int evaluer(FonctionEvaluation fonctionEvaluationGauche, FonctionEvaluation fonctionEvaluationDroite)
			throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		int evalG;
		int evalD;

		try {
			evalG = fonctionEvaluationGauche.evaluate(gauche);
			
			if (evalG == 0 && operateur.estAbsorbantAGauche()) {
				return 0;
			}
			
			evalD = fonctionEvaluationDroite.evaluate(droite);
		} catch (NonEvaluableException e) {
			 // Essaye de jeter une exception de type DependantDeStatistiquesEvaluation
			evalD = fonctionEvaluationDroite.evaluate(droite);
			throw e;
		}

		return operateur.compute(evalG, evalD);
	}
	
	@Override
	public Valeur evaluationPartielle(Affectation affectation) {
		Calcul calcul = new Calcul(gauche.evaluationPartielle(affectation), operateur, droite.evaluationPartielle(affectation));
		
		try {
			// Essaie d'évaluer pour avoir une simple valeur numérique
			int valeurMin = calcul.evaluerMin();
			int valeurMax = calcul.evaluerMax();
			
			return NewValeur.Numerique(valeurMin, valeurMax);
		} catch (NonEvaluableException | DependantDeStatistiquesEvaluation e) {
			return calcul;
		}
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






}
