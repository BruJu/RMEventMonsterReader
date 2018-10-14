package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.executeur;

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
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.CABasique;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.ConditionObjet;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.ConditionVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Calcul;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Constante;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.ExprVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Expression;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.NombreAleatoire;


public class Executeur implements ExecuteurInstructions, ExtChangeVariable.SansAffectation, ExtCondition {
	private CABasique constructeur = new CABasique();
	
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
		nouvelleAffectation(new ExprVariable(valeurGauche.idVariable), operateur, new Constante(valeurDroite.valeur));
	}
	
	@Override
	public void changerVariable(Variable valeurGauche, OpMathematique operateur, ValeurAleatoire valeurDroite) {
		nouvelleAffectation(new ExprVariable(valeurGauche.idVariable), operateur, new NombreAleatoire(valeurDroite));
	}
	
	@Override
	public void changerVariable(Variable valeurGauche, OpMathematique operateur, Variable valeurDroite) {
		nouvelleAffectation(new ExprVariable(valeurGauche.idVariable), operateur, new ExprVariable(valeurDroite.idVariable));
	}

	@Override
	public boolean getBooleenParDefaut() {
		return true;
	}

	@Override
	public boolean conditionRetourDeBase() {
		return false;
	}

	@Override
	public boolean variableVariable(int variable, Comparateur comparateur, Variable droite) {
		ExprVariable exprGauche = new ExprVariable(variable);
		Expression exprDroite = new ExprVariable(droite.idVariable);
		constructeur.commencerCondition(new ConditionVariable(exprGauche, comparateur, exprDroite));
		return true;
	}

	@Override
	public boolean variableFixe(int variable, Comparateur comparateur, ValeurFixe droite) {
		ExprVariable exprGauche = new ExprVariable(variable);
		Expression exprDroite = new Constante(droite.valeur);
		constructeur.commencerCondition(new ConditionVariable(exprGauche, comparateur, exprDroite));
		return true;
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
	public boolean interrupteur(CondInterrupteur condInterrupteur) {
		ExprVariable interrupteur = new ExprVariable(-condInterrupteur.interrupteur);
		constructeur.commencerCondition(new ConditionVariable(interrupteur, condInterrupteur.etat));
		return true;
	}

	@Override
	public boolean herosObjet(CondHerosPossedeObjet condHerosPossedeObjet) {
		constructeur.commencerCondition(new ConditionObjet(condHerosPossedeObjet));
		return true;
	}
}

