package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.executeur;

import java.util.HashMap;
import java.util.Map;

import fr.bruju.rmdechiffreur.ExecuteurInstructions;
import fr.bruju.rmdechiffreur.controlleur.ExtChangeVariable;
import fr.bruju.rmdechiffreur.controlleur.ExtCondition;
import fr.bruju.rmdechiffreur.modele.Comparateur;
import fr.bruju.rmdechiffreur.modele.Condition.CondHerosPossedeObjet;
import fr.bruju.rmdechiffreur.modele.Condition.CondInterrupteur;
import fr.bruju.rmdechiffreur.modele.OpMathematique;
import fr.bruju.rmdechiffreur.modele.ValeurAleatoire;
import fr.bruju.rmdechiffreur.modele.ValeurFixe;
import fr.bruju.rmdechiffreur.modele.Variable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.constructeur.ConstructeurValue;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.ConditionObjet;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.ConditionVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Calcul;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Constante;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.ExprVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Expression;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.NombreAleatoire;
import fr.bruju.rmeventreader.utilitaire.PileDeBooleens;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;


import static fr.bruju.rmeventreader.ProjetS.PROJET;


public class Executeur implements ExecuteurInstructions, ExtChangeVariable.SansAffectation, ExtCondition {
	
	private ConstructeurValue constructeur = new ConstructeurValue();
	
	private Map<Integer, ExprVariable> variablesInstanciees = new HashMap<>();
	
	private ExprVariable getVariable(int idVariable) {
		return Utilitaire.Maps.getY(variablesInstanciees, idVariable, ExprVariable::new);
	}
	
	private ExprVariable getVariable(Variable variable) {
		return getVariable(variable.idVariable);
	}
	
	public Algorithme extraireAlgorithme() {
		return constructeur.get();
	}
	
	private void nouvelleAffectation(ExprVariable gauche, OpMathematique operateur, Expression droite) {
		Expression droiteDuCalcul;
		
		if (operateur == OpMathematique.AFFECTATION) {
			droiteDuCalcul = droite;
		} else {
			droiteDuCalcul = new Calcul(gauche, operateur, droite);
		}
		
		constructeur.ajouter(new InstructionAffectation(gauche, droiteDuCalcul));
	}
	
	@Override
	public void changerVariable(Variable valeurGauche, OpMathematique operateur, ValeurFixe valeurDroite) {
		nouvelleAffectation(getVariable(valeurGauche), operateur, new Constante(valeurDroite.valeur));
	}
	
	@Override
	public void changerVariable(Variable valeurGauche, OpMathematique operateur, ValeurAleatoire valeurDroite) {
		nouvelleAffectation(getVariable(valeurGauche), operateur, new NombreAleatoire(valeurDroite));
	}
	
	@Override
	public void changerVariable(Variable valeurGauche, OpMathematique operateur, Variable valeurDroite) {
		nouvelleAffectation(getVariable(valeurGauche), operateur, new ExprVariable(valeurDroite.idVariable));
	}

	@Override
	public boolean getBooleenParDefaut() {
		return true;
	}

	@Override
	public int conditionRetourDeBase() {
		return 3;
	}

	@Override
	public int variableVariable(int variable, Comparateur comparateur, Variable droite) {
		ExprVariable exprGauche = new ExprVariable(variable);
		Expression exprDroite = new ExprVariable(droite.idVariable);
		return constructeur.commencerCondition(new ConditionVariable(exprGauche, comparateur, exprDroite));
	}
	
	@Override
	public int variableFixe(int variable, Comparateur comparateur, ValeurFixe droite) {
		ExprVariable exprGauche = getVariable(variable);
		Expression exprDroite = new Constante(droite.valeur);
		return constructeur.commencerCondition(new ConditionVariable(exprGauche, comparateur, exprDroite));
	}

	@Override
	public void Flot_siFin() {
		constructeur.conditionFinie();
	}

	@Override
	public void Flot_siNon() {
		constructeur.conditionElse();
	}

	@Override
	public int interrupteur(CondInterrupteur condInterrupteur) {
		ExprVariable interrupteur = getVariable(-condInterrupteur.interrupteur);
		return constructeur.commencerCondition(new ConditionVariable(interrupteur, condInterrupteur.etat));
	}

	@Override
	public int herosObjet(CondHerosPossedeObjet condHerosPossedeObjet) {
		return constructeur.commencerCondition(new ConditionObjet(condHerosPossedeObjet));
	}	
	
}

