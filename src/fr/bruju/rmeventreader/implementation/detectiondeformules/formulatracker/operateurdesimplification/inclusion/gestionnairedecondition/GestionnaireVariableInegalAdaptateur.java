package fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition;

import fr.bruju.rmdechiffreur.modele.Comparateur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.valeur.Valeur;

import java.util.Objects;

/**
 * Gestionnaire de conditions avec une base inégale
 * @author Bruju
 *
 */
public class GestionnaireVariableInegalAdaptateur implements GestionnaireDeCondition {
	// Conditions de type x • constante
	private Valeur base;
	
	
	private GestionnaireVariableInegal gestionnaire;
	

	/**
	 * Crée un gestionnaire de conditions pour la condition d'inégalité donnée
	 * @param cVariable La condition sur laquelle construire le gestionnaire
	 */
	public GestionnaireVariableInegalAdaptateur(CVariable cVariable) {
		base = cVariable.gauche;
		
		gestionnaire = new GestionnaireVariableInegal(cVariable.operateur, VConstante.evaluer(cVariable.droite));
	}


	@Override
	public Condition conditionVariable(CVariable cond) {
		if (!cond.gauche.equals(base)) {
			return cond;
		}
		
		Integer evaluationDroite = VConstante.evaluer(cond.droite);
		
		if (evaluationDroite == null)
			return cond;
		
		Boolean resultat = gestionnaire.traiter(cond.operateur, evaluationDroite);
		
		if (resultat == null)
			return cond;
		else
			return CFixe.get(resultat);
	}


	private static class GestionnaireVariableInegal {
		// Conditions de type x • constante
		private Valeur base;
		private Comparateur op;
		private int maDroite;



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

	}
}
