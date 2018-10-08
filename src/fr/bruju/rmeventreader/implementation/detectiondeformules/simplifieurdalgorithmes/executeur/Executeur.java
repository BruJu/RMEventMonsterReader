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
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.ConditionObjet;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.ConditionVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Constante;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.ExprVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.NombreAleatoire;


public class Executeur implements ExecuteurInstructions, ExtChangeVariable.SansAffectation, ExtCondition {
	private EtatMemoire etatMemoire = new EtatMemoire();
	
	
	@Override
	public void changerVariable(Variable valeurGauche, OpMathematique operateur, ValeurFixe valeurDroite) {
		etatMemoire.nouvelleInstruction(new InstructionAffectation(valeurGauche.idVariable, operateur, new Constante(valeurDroite.valeur)));
	}
	
	@Override
	public void changerVariable(Variable valeurGauche, OpMathematique operateur, ValeurAleatoire valeurDroite) {
		etatMemoire.nouvelleInstruction(new InstructionAffectation(valeurGauche.idVariable, operateur, new NombreAleatoire(valeurDroite)));
	}
	
	@Override
	public void changerVariable(Variable valeurGauche, OpMathematique operateur, Variable valeurDroite) {
		etatMemoire.nouvelleInstruction(new InstructionAffectation(valeurGauche.idVariable, operateur, new ExprVariable(valeurDroite)));
	}
	
	public Algorithme extraireAlgorithme() {
		return etatMemoire.getAlgorithme();
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
		ConditionVariable c = new ConditionVariable(new ExprVariable(variable), comparateur, new ExprVariable(droite));
		etatMemoire = etatMemoire.separer(c);
		return true;
	}

	@Override
	public boolean variableFixe(int variable, Comparateur comparateur, ValeurFixe droite) {
		ConditionVariable c = new ConditionVariable(new ExprVariable(variable), comparateur, new Constante(droite));
		etatMemoire = etatMemoire.separer(c);
		return true;
	}

	@Override
	public void Flot_siFin() {
		etatMemoire = etatMemoire.revenirAuPere();
	}

	@Override
	public void Flot_siNon() {
		etatMemoire = etatMemoire.getFrere();
	}

	@Override
	public boolean interrupteur(CondInterrupteur condInterrupteur) {
		etatMemoire = etatMemoire.separer(new ConditionVariable(condInterrupteur));
		return true;
	}

	@Override
	public boolean herosObjet(CondHerosPossedeObjet condHerosPossedeObjet) {
		etatMemoire = etatMemoire.separer(new ConditionObjet(condHerosPossedeObjet));
		return true;
	}
	
	
	
	
	

}

