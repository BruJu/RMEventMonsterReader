package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.executeur;

import fr.bruju.rmdechiffreur.ExecuteurInstructions;
import fr.bruju.rmdechiffreur.controlleur.ExtChangeVariable;
import fr.bruju.rmdechiffreur.modele.ValeurFixe;
import fr.bruju.rmdechiffreur.modele.Variable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.Algorithme;


public class Executeur implements ExecuteurInstructions, ExtChangeVariable {
	private EtatMemoire etatMemoire = new EtatMemoire();
	
	
	@Override
	public void affecterVariable(Variable valeurGauche, ValeurFixe valeurDroite) {
		algorithme.ajouterInstruction(valeurGauche.idVariable, valeurDroite.valeur);
	}

	public Algorithme extraireAlgorithme() {
		return etatMemoire.getAlgorithme();
	}
	
	@Override
	public boolean getBooleenParDefaut() {
		return true;
	}

}
