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
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionGenerale;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.Condition;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Constante;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.NombreAleatoire;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.VariableInstanciee;

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


	public void nouvelleInstruction(InstructionGenerale instruction) {
		algorithme.ajouterInstruction(instruction);
	}
	
	
	
	public Algorithme getAlgorithme() {
		return algorithme;
	}
	

	public EtatMemoireFils separer(CondInterrupteur condInterrupteur) {
		return null;
	}

	public EtatMemoireFils separer(CondHerosPossedeObjet condHerosPossedeObjet) {
		return null;
	}

	public EtatMemoireFils separer(int variable, Comparateur comparateur, Variable droite) {
		return null;
	}

	public EtatMemoireFils separer(int variable, Comparateur comparateur, ValeurFixe droite) {
		return null;
	}

	public void nouvelleInstruction(int idVariable, OpMathematique operateur, Constante constante) {
	}

	public void nouvelleInstruction(int idVariable, OpMathematique operateur, NombreAleatoire nombreAleatoire) {
	}

	public void nouvelleInstruction(int idVariable, OpMathematique operateur, Variable valeurDroite) {
	}
	
	
	
	
	
	
	
	
	
	
}
