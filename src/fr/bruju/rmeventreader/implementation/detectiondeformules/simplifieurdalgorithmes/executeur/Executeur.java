package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.executeur;

import fr.bruju.rmdechiffreur.ExecuteurInstructions;
import fr.bruju.rmdechiffreur.controlleur.ExtChangeVariable;
import fr.bruju.rmdechiffreur.controlleur.ExtCondition;
import fr.bruju.rmdechiffreur.modele.Comparateur;
import fr.bruju.rmdechiffreur.modele.Condition.CondInterrupteur;
import fr.bruju.rmdechiffreur.modele.OpMathematique;
import fr.bruju.rmdechiffreur.modele.ValeurAleatoire;
import fr.bruju.rmdechiffreur.modele.ValeurFixe;
import fr.bruju.rmdechiffreur.modele.Variable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.Condition;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.Constante;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.Instruction;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.NombreAleatoire;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.ExprVariable;


public class Executeur implements ExecuteurInstructions, ExtChangeVariable.SansAffectation, ExtCondition {
	private EtatMemoire etatMemoire = new EtatMemoire();
	
	
	@Override
	public void changerVariable(Variable valeurGauche, OpMathematique operateur, ValeurFixe valeurDroite) {
		etatMemoire.nouvelleInstruction(new Instruction(valeurGauche.idVariable, operateur, new Constante(valeurDroite.valeur)));
	}
	
	@Override
	public void changerVariable(Variable valeurGauche, OpMathematique operateur, ValeurAleatoire valeurDroite) {
		etatMemoire.nouvelleInstruction(new Instruction(valeurGauche.idVariable, operateur, new NombreAleatoire(valeurDroite)));
	}
	
	@Override
	public void changerVariable(Variable valeurGauche, OpMathematique operateur, Variable valeurDroite) {
		etatMemoire.nouvelleInstruction(new Instruction(valeurGauche.idVariable, operateur, new ExprVariable(valeurDroite)));
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
		etatMemoire = etatMemoire.separer(new Condition(new ExprVariable(variable), comparateur, new ExprVariable(droite)));
		
		return true;
	}

	@Override
	public boolean variableFixe(int variable, Comparateur comparateur, ValeurFixe droite) {
		etatMemoire = etatMemoire.separer(new Condition(new ExprVariable(variable), comparateur, new Constante(droite)));
		
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
		etatMemoire = etatMemoire.separer(new Condition(new ExprVariable(1), Comparateur.DIFFERENT, new Constante(-777)));
		
		return true;
	}
	
	
	

}
