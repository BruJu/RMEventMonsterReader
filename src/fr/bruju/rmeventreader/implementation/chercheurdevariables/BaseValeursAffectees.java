package fr.bruju.rmeventreader.implementation.chercheurdevariables;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.actionmakers.Encyclopedie;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructionsTrue;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExtChangeVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ModuleExecVariables;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.NombreObjet;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.OpMathematique;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Pointeur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ValeurDeplacable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ValeurDivers;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Variable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.VariableHeros;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

/**
 * Base de recherches des variables utilisées
 * @author Bruju
 *
 */
public class BaseValeursAffectees implements BaseDeRecherche {
	/** Liste des affectations de la variable */
	private Map<Reference, Set<Integer>> affectationsTrouvees = new HashMap<>();
	/** Variable dont on veut connaître les valeurs affectées */
	public final int variableTrackee;
	
	/**
	 * Crée une base de recherche des valeurs affectées à une variable
	 * @param idVariable Id de la variable
	 */
	public BaseValeursAffectees(int idVariable) {
		this.variableTrackee = idVariable;
	}

	@Override
	public void afficher() {
		System.out.println("== Valeurs possibles pour : " + this.variableTrackee + " " + new Encyclopedie().get("VARIABLE", this.variableTrackee));
		
		affectationsTrouvees.forEach((reference, valeurs) -> {
			String valeursS = valeurs
					.stream()
					.map(v -> v == Integer.MIN_VALUE ? "*" : v.toString())
					.collect(Collectors.joining(", "));
			System.out.println(reference.getString() + " : " + valeursS);
		});
	}

	@Override
	public ExecuteurInstructions getExecuteur(Reference ref) {
		return new Chercheur(ref);
	}
	
	/**
	 * Executeur d'instructions qui ajoute des références si il trouve des instructions en lien à des variables.
	 * 
	 * @author Bruju
	 *
	 */
	public class Chercheur implements ExecuteurInstructionsTrue, ExtChangeVariable.$$ {
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

		@Override
		public ModuleExecVariables getExecVariables() {
			return this;
		}

		@Override
		public void affecterVariable(Variable valeurGauche, ValeurFixe valeurDroite) {
			if (valeurGauche.idVariable != variableTrackee)
				return;
			Integer valeur = valeurDroite.valeur;
			
			Utilitaire.Maps.ajouterElementDansSet(affectationsTrouvees, reference, valeur);
		}
		
		/**
		 * Comportement si autre chose qu'une affectation à une variable par une valeur fixe est produit
		 * @param variable La variable modifiée
		 */
		public void autreChangement(Variable variable) {
			if (variable.idVariable != variableTrackee)
				return;
			
			Utilitaire.Maps.ajouterElementDansSet(affectationsTrouvees, reference, Integer.MIN_VALUE);
		}

		@Override
		public void affecterVariable(Variable valeurGauche, ValeurDivers valeurDroite) {
			autreChangement(valeurGauche);		}

		@Override
		public void affecterVariable(Variable valeurGauche, ValeurDeplacable valeurDroite) {
			autreChangement(valeurGauche);		}

		@Override
		public void affecterVariable(Variable valeurGauche, VariableHeros valeurDroite) {
			autreChangement(valeurGauche);		}

		@Override
		public void affecterVariable(Variable valeurGauche, NombreObjet valeurDroite) {
			autreChangement(valeurGauche);		}

		@Override
		public void affecterVariable(Variable valeurGauche, ValeurAleatoire valeurDroite) {
			autreChangement(valeurGauche);		}

		@Override
		public void affecterVariable(Variable valeurGauche, Pointeur valeurDroite) {
			autreChangement(valeurGauche);		}

		@Override
		public void affecterVariable(Variable valeurGauche, Variable valeurDroite) {
			autreChangement(valeurGauche);		}

		@Override
		public void changerVariable(Variable valeurGauche, OpMathematique operateur, ValeurDivers valeurDroite) {
			autreChangement(valeurGauche);		}

		@Override
		public void changerVariable(Variable valeurGauche, OpMathematique operateur, ValeurDeplacable valeurDroite) {
			$$.super.changerVariable(valeurGauche, operateur, valeurDroite);
		}

		@Override
		public void changerVariable(Variable valeurGauche, OpMathematique operateur, VariableHeros valeurDroite) {
			$$.super.changerVariable(valeurGauche, operateur, valeurDroite);
		}

		@Override
		public void changerVariable(Variable valeurGauche, OpMathematique operateur, NombreObjet valeurDroite) {
			autreChangement(valeurGauche);		}

		@Override
		public void changerVariable(Variable valeurGauche, OpMathematique operateur, ValeurAleatoire valeurDroite) {
			autreChangement(valeurGauche);		}

		@Override
		public void changerVariable(Variable valeurGauche, OpMathematique operateur, Pointeur valeurDroite) {
			autreChangement(valeurGauche);		}

		@Override
		public void changerVariable(Variable valeurGauche, OpMathematique operateur, Variable valeurDroite) {
			autreChangement(valeurGauche);		}

		@Override
		public void changerVariable(Variable valeurGauche, OpMathematique operateur, ValeurFixe valeurDroite) {
			autreChangement(valeurGauche);
		}
		
		
	}
}
