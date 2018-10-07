package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.executeur;

import fr.bruju.rmdechiffreur.ExecuteurInstructions;
import fr.bruju.rmdechiffreur.controlleur.ExtChangeVariable;
import fr.bruju.rmdechiffreur.modele.ValeurFixe;
import fr.bruju.rmdechiffreur.modele.Variable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.Instruction;


public class Executeur implements ExecuteurInstructions, ExtChangeVariable {

	private Algorithme algorithme = new Algorithme();
	
	
	@Override
	public void affecterVariable(Variable valeurGauche, ValeurFixe valeurDroite) {
		algorithme.ajouterInstruction(new Instruction(valeurGauche.idVariable, valeurDroite.valeur));
	}

	public Algorithme extraireAlgorithme() {
		return algorithme;
	}
	
	@Override
	public boolean getBooleenParDefaut() {
		return true;
	}

}
