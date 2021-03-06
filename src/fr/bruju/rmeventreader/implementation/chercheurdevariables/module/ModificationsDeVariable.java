package fr.bruju.rmeventreader.implementation.chercheurdevariables.module;

import java.util.*;
import java.util.stream.Collectors;

import fr.bruju.rmdechiffreur.ExecuteurInstructions;
import fr.bruju.rmdechiffreur.controlleur.ExtChangeVariable;
import fr.bruju.rmdechiffreur.modele.NombreObjet;
import fr.bruju.rmdechiffreur.modele.OpMathematique;
import fr.bruju.rmdechiffreur.modele.Pointeur;
import fr.bruju.rmdechiffreur.modele.ValeurAleatoire;
import fr.bruju.rmdechiffreur.modele.ValeurDeplacable;
import fr.bruju.rmdechiffreur.modele.ValeurDivers;
import fr.bruju.rmdechiffreur.modele.ValeurFixe;
import fr.bruju.rmdechiffreur.modele.Variable;
import fr.bruju.rmdechiffreur.modele.VariableHeros;
import fr.bruju.rmdechiffreur.reference.Reference;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.BaseDeRecherche;
import fr.bruju.util.MapsUtils;

import static fr.bruju.rmeventreader.ProjetS.PROJET;

/**
 * Base de recherches des modifications apportées à une variable
 * @author Bruju
 *
 */
public class ModificationsDeVariable implements BaseDeRecherche {
	/** Variable dont on veut connaître les valeurs */
	public final int variableTrackee;
	/** Liste des modifications de la variable */
	private Map<Reference, Set<Modification>> affectationsTrouvees = new TreeMap<>();
	
	/**
	 * Crée une base de recherche des valeurs affectées à une variable
	 * @param idVariable Id de la variable
	 */
	public ModificationsDeVariable(int idVariable) {
		this.variableTrackee = idVariable;
	}

	@Override
	public void afficher() {
		System.out.println("== Valeurs possibles pour : " + this.variableTrackee + " " + PROJET.extraireVariable(variableTrackee));
		
		affectationsTrouvees.forEach((reference, valeurs) -> {
			StringJoiner sj = new StringJoiner(", ");

			for (Modification valeur : valeurs) {
				sj.add(valeur.toString());
			}

			System.out.println(reference.getString() + " : " + sj.toString());
		});
	}

	@Override
	public ExecuteurInstructions getExecuteur(Reference ref) {
		return new Chercheur(ref);
	}
	
	
	private static class Modification implements Comparable<Modification> {
		public final OpMathematique operateur;
		public final Integer valeur;
		
		public Modification(OpMathematique operateur) {
			this.operateur = operateur;
			valeur = Integer.MIN_VALUE;
		}
		
		public Modification(OpMathematique operateur, Integer valeur) {
			this.operateur = operateur;
			this.valeur = valeur;
		}

		@Override
		public int compareTo(Modification arg0) {
			int cmp = Integer.compare(this.operateur.ordinal(), arg0.operateur.ordinal());
			
			if (cmp != 0) {
				return cmp;
			}
			
			return Integer.compare(valeur, arg0.valeur);
		}
		
		@Override
		public String toString() {
			String valeurEnString = ((valeur == Integer.MIN_VALUE) ? "*" : Integer.toString(valeur));
			
			if (operateur == OpMathematique.AFFECTATION)
				return valeurEnString;
			
			return operateur.symbole + " " + valeurEnString; 
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(operateur, valeur);
		}
		
		@Override
		public boolean equals (Object object) {
			if (object instanceof Modification) {
				Modification that = (Modification) object;
				return Objects.equals(this.operateur, that.operateur)
						&& Objects.equals(this.valeur, that.valeur);
			}
			return false;
		}
		
	}

	/**
	 * Executeur d'instructions qui ajoute des références si il trouve des instructions en lien à des variables.
	 * 
	 * @author Bruju
	 *
	 */
	private class Chercheur implements ExecuteurInstructions, ExtChangeVariable.SansAffectation {
		/** Référence */
		private Reference reference;

		/**
		 * Crée un nouveau chercheur de références à une variable
		 * 
		 * @param reference La référence
		 */
		public Chercheur(Reference reference) {
			this.reference = reference;
		}
		
		@Override
		public boolean getBooleenParDefaut() {
			return true;
		}
		
		/**
		 * Comportement si autre chose qu'une affectation à une variable par une valeur fixe est produit
		 * @param variable La variable modifiée
		 */
		public void autreChangement(Variable variable, OpMathematique operateur) {
			if (variable.idVariable != variableTrackee)
				return;
			
			MapsUtils.ajouterElementDansSet(affectationsTrouvees, reference, new Modification(operateur));
		}

		@Override
		public void changerVariable(Variable valeurGauche, OpMathematique operateur, ValeurDivers valeurDroite) {
			autreChangement(valeurGauche, operateur);
		}

		@Override
		public void changerVariable(Variable valeurGauche, OpMathematique operateur, ValeurDeplacable valeurDroite) {
			autreChangement(valeurGauche, operateur);
		}

		@Override
		public void changerVariable(Variable valeurGauche, OpMathematique operateur, VariableHeros valeurDroite) {
			autreChangement(valeurGauche, operateur);
		}

		@Override
		public void changerVariable(Variable valeurGauche, OpMathematique operateur, NombreObjet valeurDroite) {
			autreChangement(valeurGauche, operateur);
		}

		@Override
		public void changerVariable(Variable valeurGauche, OpMathematique operateur, ValeurAleatoire valeurDroite) {
			autreChangement(valeurGauche, operateur);
		}

		@Override
		public void changerVariable(Variable valeurGauche, OpMathematique operateur, Pointeur valeurDroite) {
			autreChangement(valeurGauche, operateur);
		}

		@Override
		public void changerVariable(Variable valeurGauche, OpMathematique operateur, Variable valeurDroite) {
			autreChangement(valeurGauche, operateur);
		}

		@Override
		public void changerVariable(Variable valeurGauche, OpMathematique operateur, ValeurFixe valeurDroite) {
			if (valeurGauche.idVariable != variableTrackee)
				return;
			
			Integer valeur = valeurDroite.valeur;
			
			MapsUtils.ajouterElementDansSet(affectationsTrouvees, reference, new Modification(operateur, valeur));
		}
	}
}
