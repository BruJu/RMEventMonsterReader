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
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionGenerale;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.Condition;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.ConditionObjet;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.ConditionVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Calcul;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.CaseMemoire;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Constante;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Expression;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.NombreAleatoire;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.VariableInstanciee;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

public abstract class EtatMemoire {
	protected Algorithme algorithme = new Algorithme();
	
	private Condition conditionSeparatrice;
	protected EtatMemoireFils filsGauche;
	protected EtatMemoireFils filsDroit;

	private Map<Integer, VariableInstanciee> variablesActuelles = new HashMap<>();
	
	public EtatMemoireFils separer(Condition conditionSeparatrice) {
		this.conditionSeparatrice = conditionSeparatrice;
		filsGauche = new EtatMemoireFils(this);
		filsDroit = new EtatMemoireFils(this);
		return filsGauche;
	}

	
	protected final void accumulerFils() {
		algorithme.ajouterCondition(conditionSeparatrice, filsGauche.getAlgorithme(), filsDroit.getAlgorithme());
		filsGauche = null;
		filsDroit = null;
	}


	private void nouvelleInstruction(InstructionGenerale instruction) {
		algorithme.ajouterInstruction(instruction);
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
		VariableInstanciee ancienEtatGauche = getValeur(idVariable);
		
		Expression droiteDuCalcul;
		
		if (operateur == OpMathematique.AFFECTATION) {
			droiteDuCalcul = droite;
		} else {
			droiteDuCalcul = new Calcul(ancienEtatGauche, operateur, droite);
		}
		
		nouvelleInstruction(new InstructionAffectation(ancienEtatGauche.nouvelleInstance(), droiteDuCalcul));
	}
	


	public void nouvelleInstruction(int idVariable, OpMathematique operateur, Variable valeurDroite) {
		Expression droite = getValeur(valeurDroite.idVariable);
		nouvelleInstruction(idVariable, operateur, droite);
	}
	
	

	public final VariableInstanciee getValeur(int numeroDeCase) {
		if (variablesActuelles.containsKey(numeroDeCase)) {
			return variablesActuelles.get(numeroDeCase);
		} else {
			return getValeurManquante(numeroDeCase);
		}
	}
	
	protected abstract VariableInstanciee getValeurManquante(int numeroDeCase);
	
	
	
	
	
}
