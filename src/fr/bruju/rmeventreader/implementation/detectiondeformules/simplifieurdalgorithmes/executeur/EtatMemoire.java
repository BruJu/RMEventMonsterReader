package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.executeur;

import java.util.HashMap;
import java.util.Map;

import fr.bruju.rmdechiffreur.modele.Comparateur;
import fr.bruju.rmdechiffreur.modele.OpMathematique;
import fr.bruju.rmdechiffreur.modele.ValeurFixe;
import fr.bruju.rmdechiffreur.modele.Variable;
import fr.bruju.rmdechiffreur.modele.Condition.CondHerosPossedeObjet;
import fr.bruju.rmdechiffreur.modele.Condition.CondInterrupteur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.BlocConditionnel;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.Condition;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.ConditionObjet;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.ConditionVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Calcul;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Constante;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.ExprVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Expression;
import fr.bruju.rmeventreader.utilitaire.Utilitaire.Maps;

public final class EtatMemoire {
	/* =========
	 * ATTRIBUTS
	 * ========= */
	
	// Map globale des variables instanciées (permet d'avoir toujours le même objet)
	private final Map<Integer, ExprVariable> variablesActuelles;
	
	// Identification d'un état mémoire
	public final EtatMemoire pere;
	public final Algorithme algorithme;
	
	// Gestion de la structure d'arbre
	private Condition conditionSeparatrice;
	protected EtatMemoire filsGauche;
	protected EtatMemoire filsDroit;


	/* =============
	 * CONSTRUCTEURS
	 * ============= */
	
	public EtatMemoire() {
		this.pere = null;
		this.variablesActuelles = new HashMap<>();
		this.algorithme = new Algorithme();
	}
	
	public EtatMemoire(EtatMemoire pere) {
		this.pere = pere;
		this.variablesActuelles = pere.variablesActuelles;
		this.algorithme = new Algorithme();
	}
	

	
	
	public EtatMemoire separer(Condition conditionSeparatrice) {
		this.conditionSeparatrice = conditionSeparatrice;		
		filsGauche = new EtatMemoire(this);
		filsDroit = new EtatMemoire(this);
		return filsGauche;
	}

	
	protected final void accumulerFils() {
		Boolean test = conditionSeparatrice.tester();
		if (test != null) {
			EtatMemoire fils = test ? filsGauche : filsDroit;
			accumulerUnSeulFils(fils);
		} else {
			Algorithme brancheVrai = filsGauche.algorithme;
			Algorithme brancheFaux = filsDroit.algorithme;
			BlocConditionnel instruction = new BlocConditionnel(conditionSeparatrice, brancheVrai, brancheFaux);
			algorithme.ajouterInstruction(instruction);
		}
		
		filsGauche = null;
		filsDroit = null;
	}
	
	private void accumulerUnSeulFils(EtatMemoire fils) {
		Algorithme algorithme = fils.algorithme;
		this.algorithme.ajouter(algorithme);
		
		fils.variablesActuelles.forEach(variablesActuelles::put);
	}




	

	public EtatMemoire separer(CondInterrupteur condInterrupteur) {
		return separer(new ConditionVariable(getValeur(-condInterrupteur.interrupteur), condInterrupteur.etat));
	}

	public EtatMemoire separer(CondHerosPossedeObjet condHerosPossedeObjet) {
		return separer(new ConditionObjet(condHerosPossedeObjet));
	}

	public EtatMemoire separer(int variable, Comparateur comparateur, Variable droite) {
		Expression exprGauche = getValeur(variable);
		Expression exprDroite = getValeur(droite.idVariable);
		return separer(new ConditionVariable(exprGauche, comparateur, exprDroite));
	}

	public EtatMemoire separer(int variable, Comparateur comparateur, ValeurFixe droite) {
		Expression exprGauche = getValeur(variable);
		return separer(new ConditionVariable(exprGauche, comparateur, new Constante(droite.valeur)));
	}

	public void nouvelleInstruction(int idVariable, OpMathematique operateur, Expression droite) {
		ExprVariable variableGauche = getValeur(idVariable);
		
		Expression droiteDuCalcul;
		
		if (operateur == OpMathematique.AFFECTATION) {
			droiteDuCalcul = droite;
		} else {
			droiteDuCalcul = new Calcul(variableGauche, operateur, droite);
		}
		
		this.variablesActuelles.put(idVariable, variableGauche);
		
		algorithme.ajouterInstruction(new InstructionAffectation(variableGauche, droiteDuCalcul));
	}
	


	public void nouvelleInstruction(int idVariable, OpMathematique operateur, Variable valeurDroite) {
		Expression droite = getValeur(valeurDroite.idVariable);
		nouvelleInstruction(idVariable, operateur, droite);
	}
	
	public final ExprVariable getValeur(int numeroDeCase) {
		return Maps.getAvecInitialisation(variablesActuelles, numeroDeCase, ExprVariable::new);
	}
	
	// Etat Memoire fils
	
	public EtatMemoire getFrere() {
		return pere.filsDroit;
	}
	
	public EtatMemoire revenirAuPere() {
		pere.accumulerFils();
		return pere;
	}


	
}
