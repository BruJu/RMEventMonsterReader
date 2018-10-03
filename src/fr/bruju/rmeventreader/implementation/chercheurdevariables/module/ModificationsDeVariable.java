package fr.bruju.rmeventreader.implementation.chercheurdevariables.module;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.actionmakers.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.controlleur.ExecuteurInstructionsTrue;
import fr.bruju.rmeventreader.actionmakers.controlleur.ExtChangeVariable;
import fr.bruju.rmeventreader.actionmakers.modele.NombreObjet;
import fr.bruju.rmeventreader.actionmakers.modele.OpMathematique;
import fr.bruju.rmeventreader.actionmakers.modele.Pointeur;
import fr.bruju.rmeventreader.actionmakers.modele.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.modele.ValeurDeplacable;
import fr.bruju.rmeventreader.actionmakers.modele.ValeurDivers;
import fr.bruju.rmeventreader.actionmakers.modele.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.modele.Variable;
import fr.bruju.rmeventreader.actionmakers.modele.VariableHeros;
import fr.bruju.rmeventreader.actionmakers.reference.Reference;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.BaseDeRecherche;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;
import java.util.Objects;

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
	private Map<Reference, Set<Modification>> affectationsTrouvees = new HashMap<>();
	
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
			String valeursS = valeurs
					.stream()
					.map(v -> v.toString())
					.collect(Collectors.joining(", "));
			System.out.println(reference.getString() + " : " + valeursS);
		});
	}

	@Override
	public ExecuteurInstructions getExecuteur(Reference ref) {
		return new Chercheur(ref);
	}
	
	
	public static class Modification implements Comparable<Modification> {
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
						&& this.valeur == that.valeur;
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
	public class Chercheur implements ExecuteurInstructionsTrue, ExtChangeVariable.SansAffectation {
		/** Référence */
		private Reference reference;

		/**
		 * Crée un nouveau chercheur de références à une variable
		 * 
		 * @param reference La référence
		 * @param variablesCherchees Map à compléter
		 */
		public Chercheur(Reference reference) {
			this.reference = reference;
		}
		
		/**
		 * Comportement si autre chose qu'une affectation à une variable par une valeur fixe est produit
		 * @param variable La variable modifiée
		 */
		public void autreChangement(Variable variable, OpMathematique operateur) {
			if (variable.idVariable != variableTrackee)
				return;
			
			Utilitaire.Maps.ajouterElementDansSet(affectationsTrouvees, reference, new Modification(operateur));
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
			
			Utilitaire.Maps.ajouterElementDansSet(affectationsTrouvees, reference, new Modification(operateur, valeur));
		}

		
		
	}
}
