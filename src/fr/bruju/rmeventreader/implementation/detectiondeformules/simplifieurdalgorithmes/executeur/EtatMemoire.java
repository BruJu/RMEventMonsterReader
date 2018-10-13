package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.executeur;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import fr.bruju.rmdechiffreur.modele.Comparateur;
import fr.bruju.rmdechiffreur.modele.OpMathematique;
import fr.bruju.rmdechiffreur.modele.ValeurFixe;
import fr.bruju.rmdechiffreur.modele.Variable;
import fr.bruju.rmdechiffreur.modele.Condition.CondHerosPossedeObjet;
import fr.bruju.rmdechiffreur.modele.Condition.CondInterrupteur;
import fr.bruju.rmeventreader.implementation.detectiondeformules._variables.EtatInitial;
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
	private final Map<Integer, ExprVariable> variables;
	
	// Identification d'un état mémoire
	public final EtatMemoire pere;
	public final Algorithme algorithme;
	
	// Gestion de la structure d'arbre
	private Condition conditionSeparatrice;
	protected EtatMemoire filsGauche;
	protected EtatMemoire filsDroit;

	
	// Etat des variables
	private Map<Integer, Integer> valeursActuelles;

	/* =============
	 * CONSTRUCTEURS
	 * ============= */
	
	public EtatMemoire() {
		this.pere = null;
		this.variables = new HashMap<>();
		this.algorithme = new Algorithme();
		valeursActuelles = new HashMap<>();
		
		remplirValeursActuelles();
	}
	
	private void remplirValeursActuelles() {
		EtatInitial etatInitial = EtatInitial.getEtatInitial();
		
		etatInitial.forEach(this::initialiserValeur);
	}

	public EtatMemoire(EtatMemoire pere) {
		this.pere = pere;
		this.variables = pere.variables;
		this.algorithme = new Algorithme();
		valeursActuelles = new HashMap<>(pere.valeursActuelles);
	}
	

	private void initialiserValeur(Integer idVariable, Integer valeur) {
		variables.put(idVariable, new ExprVariable(idVariable));
		valeursActuelles.put(idVariable, valeur);
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
			algorithme.ajouter(fils.algorithme);
			valeursActuelles = fils.valeursActuelles;
		} else {
			Algorithme brancheVrai = filsGauche.algorithme;
			Algorithme brancheFaux = filsDroit.algorithme;
			BlocConditionnel instruction = new BlocConditionnel(conditionSeparatrice, brancheVrai, brancheFaux);
			algorithme.ajouterInstruction(instruction);
			combinerValeursActuelles();
		}
		
		filsGauche = null;
		filsDroit = null;
	}

	

	private void combinerValeursActuelles() {
		this.valeursActuelles.clear();
		this.variables.keySet().forEach(this::actualiserValeurActuelle);
	}
	
	private void actualiserValeurActuelle(Integer idVariable) {
		Integer vrai = filsGauche.valeursActuelles.get(idVariable);
		Integer faux = filsGauche.valeursActuelles.get(idVariable);
		
		if (Objects.equals(vrai, faux)) {
			valeursActuelles.put(idVariable, vrai);
		}
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
		ExprVariable variableGauche = getVariable(idVariable);
		
		Expression droiteDuCalcul;
		
		if (operateur == OpMathematique.AFFECTATION) {
			droiteDuCalcul = droite;
		} else {
			droiteDuCalcul = new Calcul(getValeur(idVariable), operateur, droite);
		}
		
		valeursActuelles.put(idVariable, droiteDuCalcul.evaluer());
		
		algorithme.ajouterInstruction(new InstructionAffectation(variableGauche, droiteDuCalcul));
	}
	


	public void nouvelleInstruction(int idVariable, OpMathematique operateur, Variable valeurDroite) {
		Expression droite = getVariable(valeurDroite.idVariable);
		nouvelleInstruction(idVariable, operateur, droite);
	}
	
	
	public final Expression getValeur(int idVariable) {
		Integer valeur = valeursActuelles.get(idVariable);
		
		return valeur != null ? new Constante(valeur) : getVariable(idVariable);
	}
	
	public final ExprVariable getVariable(int numeroDeCase) {
		return Maps.getAvecInitialisation(variables, numeroDeCase, ExprVariable::new);
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
