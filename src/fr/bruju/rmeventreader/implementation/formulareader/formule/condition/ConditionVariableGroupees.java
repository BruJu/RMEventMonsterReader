package fr.bruju.rmeventreader.implementation.formulareader.formule.condition;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.simple.ValeurNumerique;
import fr.bruju.rmeventreader.utilitaire.Pair;

/**
 * Cette classe a pour but de permettre de savoir si un affichage ensembliste est possible pour une liste de
 * conditions de ValeurConditionnelle.
 * 
 * ie si toutes les conditions sont de type si x = 1 alors y = 2 ; si x = 2 alors y = 4 (...), on souhaite afficher
 * y = x[1,2,3,4] -> [2,4,8,16].
 * 
 * Ces fonctions sont déportées dans une classe spécifique afin de ne pas surcharger l'interface Condition et de ne
 * pas surcharger la classe ValeurConditionnelle avec un traitement exceptionnel.
 * 
 * @author Bruju
 *
 */
public class ConditionVariableGroupees {

	private ConditionVariableGroupees() {
		// Non instanciable
	}
	
	/**
	 * Renvoie vrai si toutes les conditions de la liste sont de type variable sur la même variable
	 * @param liste La liste à tester
	 * @return Vrai si toutes les conditions de la liste sont de type variable et sur la même variable.
	 * Faux pour une liste vide, faux pour une liste d'un seul élément.
	 */
	public static boolean affichageEnsemblistePossible(List<Condition> liste) {
		// Taille de la liste
		if (liste.size() < 2) {
			return false;
		}
		
		// Type de la première condition
		Condition premiere = liste.get(0);
		if (!(premiere instanceof ConditionVariable)) {
			return false;
		}
		
		// Recupération de la variable concernée
		ConditionVariable prem = (ConditionVariable) premiere;
		Valeur variable = prem.getGauche();
		
		
		// Teste de toutes les conditions
		for (Condition condition : liste) {
			if (!(condition instanceof ConditionVariable))
				return false;
			
			ConditionVariable condVar = (ConditionVariable) condition;
			
			if (condVar.getGauche() != variable || condVar.getOperator() != Operator.IDENTIQUE
					|| !(condVar.getDroite() instanceof ValeurNumerique)) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Renvoie une représentation ensembliste de la liste donnée, en supposant que la liste de conditions qu'elle
	 * contient renvoie vrai avec affichageEnsemblistePossible
	 * @param listePaire La liste à afficher
	 * @return Une représentation ensembliste
	 */
	public static String getStringEnsembliste(List<Pair<Condition, Valeur>> listePaire) {
		String valeursDepart = "[";
		String valeursSortie = "[";
		String valeurTestee = ((ConditionVariable) listePaire.get(0).getLeft()).getGauche().getString();

		for (Pair<Condition, Valeur> paireCondValeur : listePaire) {
			ConditionVariable c = (ConditionVariable) paireCondValeur.getLeft();

			valeursDepart = valeursDepart + c.getDroite().getString() + ", ";
			valeursSortie = valeursSortie + paireCondValeur.getRight().getString() + ", ";

		}

		valeursDepart = valeursDepart.substring(0, valeursDepart.length() - 2) + "]";
		valeursSortie = valeursSortie.substring(0, valeursSortie.length() - 2) + "]";

		return "{" + valeurTestee + valeursDepart + " = " + valeursSortie + "}";
	}

}
