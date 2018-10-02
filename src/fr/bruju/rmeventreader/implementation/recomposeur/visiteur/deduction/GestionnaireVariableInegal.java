package fr.bruju.rmeventreader.implementation.recomposeur.visiteur.deduction;

import java.util.Objects;

import fr.bruju.rmeventreader.actionmakers.modele.Comparateur;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionFixe;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionValeur;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Constante;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Valeur;
import fr.bruju.rmeventreader.utilitaire.Pair;

/**
 * Gestionnaire de conditions avec une base inégale
 * @author Bruju
 *
 */
public class GestionnaireVariableInegal implements GestionnaireDeCondition {
	// Conditions de type x • constante
	private Valeur base;
	private Comparateur op;
	private int maDroite;

	/**
	 * Crée un gestionnaire de conditions pour la condition d'inégalité donnée
	 * @param cVariable La condition sur laquelle construire le gestionnaire
	 */
	public GestionnaireVariableInegal(ConditionValeur cVariable) {
		this.base = cVariable.gauche;

		
		Pair<Comparateur, Integer> e = evaluerSansBorne(cVariable);
		op = e.getLeft();
		maDroite = e.getRight();
		
		op = cVariable.operateur;

		Integer md = Constante.evaluer(cVariable.droite);

		maDroite = md;
	}
	
	
	/**
	 * Crée un gestionnaire de conditions avec un comparateur et une valeur à droite
	 * @param op L'opérateur
	 * @param maDroite La valeur droite
	 */
	public GestionnaireVariableInegal(Comparateur op, int maDroite) {
		this.base = null;
		this.op = op;
		this.maDroite = maDroite;
	}



	/**
	 * Transforme la condition en une condition sans inégalités contenant un égal et donne la valeur de sa partie droite
	 * @param cVariable La condition
	 * @return Une paire opérateur et évaluation de la partie droite
	 */
	private Pair<Comparateur, Integer> evaluerSansBorne(ConditionValeur cVariable) {
		Integer evaluation = Constante.evaluer(cVariable.droite);
		
		if (evaluation == null)
			return null;
		
		Comparateur operateur = cVariable.operateur;
		
		if (operateur == Comparateur.INFEGAL) {
			// x <= 3 équivaut = x < 4
			evaluation ++;
			operateur = Comparateur.INF;
		}
		if (operateur == Comparateur.SUPEGAL) {
			// x >= 3 équivaut à x > 2
			evaluation --;
			operateur = Comparateur.SUP;
		}
		
		return new Pair<>(operateur, evaluation);
	}

	/**
	 * Traite une condition partiellement décrite avec ce gestionnaire
	 * @param autreComparateur Le symbole de la condition à intégrer
	 * @param autreEvaluation L'évaluation de la partie droite de la condition à intégrer
	 * @return null si les deux conditions décrivent des parties différentes du domaine,
	 * true si elle est toujours respectée, faux si les domaines décrits par les deux conditions sont incompatibles
	 */
	public Boolean traiter(Comparateur autreComparateur, int autreEvaluation) {
		/* DIFFERENT */
		if (autreComparateur == Comparateur.DIFFERENT) {
			return null;
		}
		
		/*
		 * IDENTIQUE
		 */
		if (autreComparateur == Comparateur.IDENTIQUE) {
			if (op.test(autreEvaluation, maDroite)) {
				// Est vérifiable, mais pas toujours
				return null;
			} else {
				// N'est jamais vérifiée
				return Boolean.FALSE;
			}
		}
		
		/*
		 * OPERATEURS INEGAUX
		 */
		
		if (op == autreComparateur) {
			if (op.test(autreEvaluation, maDroite)) {
				return null;
			} else {
				return Boolean.TRUE;
			}
		} else {
			// pour inférieur
			// on veut qu'il y ait au moins un x tel que ma droite < x < sa droite
			// <=> sa droite - ma droite >= 2
			if (op == Comparateur.INF) {
				if (autreEvaluation - maDroite >= 2) {
					return null;
				} else {
					return Boolean.FALSE;
				}
			}
			
			
			// pour supérieur
			// on veut au moins un x tel que sadroite < x < madroite
			// <=> madroite - sadroite >= 2
			if (op == Comparateur.SUP) {
				if (-autreEvaluation + maDroite >= 2) {
					return null;
				} else {
					return Boolean.FALSE;
				}
			}
		}

		return null;
	}
	
	@Override
	public Condition conditionVariable(ConditionValeur cond) {
		if (!(Objects.equals(base, cond.gauche))) {
			return cond;
		}
		
		/* DIFFERENT */
		if (cond.operateur == Comparateur.DIFFERENT) {
			return cond;
		}
		
		Pair<Comparateur, Integer> autreEv = evaluerSansBorne(cond);
		
		if (autreEv == null) {
			return cond;
		}
		
		Comparateur autreOp = autreEv.getLeft();
		int saDroite = autreEv.getRight();

		Boolean resultat = traiter(autreOp, saDroite);
		
		if (resultat == null) {
			return cond;
		} else {
			return ConditionFixe.get(resultat);
		}
	}

}
