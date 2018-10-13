package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.executeur;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

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

public abstract class EtatMemoire {
	protected Algorithme algorithme = new Algorithme();
	
	private Condition conditionSeparatrice;
	protected EtatMemoireFils filsGauche;
	protected EtatMemoireFils filsDroit;

	private Map<Integer, ExprVariable> variablesActuelles = new HashMap<>();
	

	
	public EtatMemoireFils separer(Condition conditionSeparatrice) {
		this.conditionSeparatrice = conditionSeparatrice;		
		filsGauche = new EtatMemoireFils(this);
		filsDroit = new EtatMemoireFils(this);
		return filsGauche;
	}

	
	protected final void accumulerFils() {
		Boolean test = conditionSeparatrice.tester();
		if (test != null) {
			EtatMemoireFils fils = test ? filsGauche : filsDroit;
			accumulerUnSeulFils(fils);
		} else {
			Algorithme brancheVrai = filsGauche.getAlgorithme();
			Algorithme brancheFaux = filsDroit.getAlgorithme();
			BlocConditionnel instruction = new BlocConditionnel(conditionSeparatrice, brancheVrai, brancheFaux);
			algorithme.ajouterInstruction(instruction);
		}
		
		filsGauche = null;
		filsDroit = null;
	}
	
	private void accumulerUnSeulFils(EtatMemoireFils fils) {
		Algorithme algorithme = fils.getAlgorithme();
		this.algorithme.ajouter(algorithme);
		
		((EtatMemoire) fils).variablesActuelles.forEach(variablesActuelles::put);
	}





	
	public Algorithme getAlgorithme() {
		return algorithme;
	}
	

	public EtatMemoireFils separer(CondInterrupteur condInterrupteur) {
		return separer(new ConditionVariable(getValeur(-condInterrupteur.interrupteur), condInterrupteur.etat));
	}

	public EtatMemoireFils separer(CondHerosPossedeObjet condHerosPossedeObjet) {
		return separer(new ConditionObjet(condHerosPossedeObjet));
	}

	public EtatMemoireFils separer(int variable, Comparateur comparateur, Variable droite) {
		Expression exprGauche = getValeur(variable);
		Expression exprDroite = getValeur(droite.idVariable);
		return separer(new ConditionVariable(exprGauche, comparateur, exprDroite));
	}

	public EtatMemoireFils separer(int variable, Comparateur comparateur, ValeurFixe droite) {
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
		if (variablesActuelles.containsKey(numeroDeCase)) {
			return variablesActuelles.get(numeroDeCase);
		} else {
			return getValeurManquante(numeroDeCase);
		}
	}
	
	protected abstract ExprVariable getValeurManquante(int numeroDeCase);
	
	
	
	
	
}
