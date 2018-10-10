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
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionGenerale;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.Condition;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.ConditionObjet;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.ConditionVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.AgregatDeVariables;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Calcul;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Constante;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Expression;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.VariableInstanciee;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.VariableUtilisee;

public abstract class EtatMemoire {
	protected Algorithme algorithme = new Algorithme();
	
	private Condition conditionSeparatrice;
	protected EtatMemoireFils filsGauche;
	protected EtatMemoireFils filsDroit;

	private Map<Integer, VariableUtilisee> variablesActuelles = new HashMap<>();
	

	
	public EtatMemoireFils separer(Condition conditionSeparatrice) {
		this.conditionSeparatrice = conditionSeparatrice;
		filsGauche = new EtatMemoireFils(this);
		filsDroit = new EtatMemoireFils(this);
		return filsGauche;
	}

	
	protected final void accumulerFils() {
		algorithme.ajouterCondition(conditionSeparatrice, filsGauche.getAlgorithme(), filsDroit.getAlgorithme());
		integrerInstanciations();
		filsGauche = null;
		filsDroit = null;
	}
	
	private final void integrerInstanciations() {
		Stream.of(filsGauche, filsDroit)
		      .map(etat -> ((EtatMemoire) etat).variablesActuelles.keySet())
		      .flatMap(Set::stream)
		      .forEach(this::actualiserViaFils);		
	}


	private final void actualiserViaFils(int numeroDeVariable) {
		VariableUtilisee variableGauche = filsGauche.getValeur(numeroDeVariable);
		VariableUtilisee variableDroite = filsDroit.getValeur(numeroDeVariable);
		VariableUtilisee agregat = AgregatDeVariables.combiner(variableGauche, variableDroite);
		variablesActuelles.put(numeroDeVariable, agregat);
	}


	/*
	private void nouvelleInstruction(InstructionGenerale instruction) {
		algorithme.ajouterInstruction(instruction);
	}
	*/
	
	
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
		if (algorithme.accumuler(idVariable, operateur, droite)) {
			return;
		}
		
		VariableUtilisee ancienEtatGauche = getValeur(idVariable);
		
		Expression droiteDuCalcul;
		
		if (operateur == OpMathematique.AFFECTATION) {
			droiteDuCalcul = droite;
		} else {
			droiteDuCalcul = new Calcul(ancienEtatGauche, operateur, droite);
		}
		
		VariableInstanciee nouvelleInstance = ancienEtatGauche.nouvelleInstance();
		this.variablesActuelles.put(idVariable, nouvelleInstance);
		
		algorithme.ajouterInstruction(new InstructionAffectation(nouvelleInstance, droiteDuCalcul));
	}
	


	public void nouvelleInstruction(int idVariable, OpMathematique operateur, Variable valeurDroite) {
		Expression droite = getValeur(valeurDroite.idVariable);
		nouvelleInstruction(idVariable, operateur, droite);
	}
	
	

	public final VariableUtilisee getValeur(int numeroDeCase) {
		if (variablesActuelles.containsKey(numeroDeCase)) {
			return variablesActuelles.get(numeroDeCase);
		} else {
			return getValeurManquante(numeroDeCase);
		}
	}
	
	protected abstract VariableUtilisee getValeurManquante(int numeroDeCase);
	
	
	
	
	
}
