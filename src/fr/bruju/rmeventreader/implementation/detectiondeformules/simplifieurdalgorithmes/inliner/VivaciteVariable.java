package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.inliner;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionGenerale;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

import java.util.List;

public class VivaciteVariable {

	private Etat etat;
	private InstructionGenerale instructionUtilisatrice;

	public VivaciteVariable() {
		//etat = Etat.Vivant;
	}

	public VivaciteVariable(Etat etat) {
		this.etat = etat;
		instructionUtilisatrice = null;
	}


	public Etat getEtat() {
		return etat;
	}


	public VivaciteVariable(VivaciteVariable vivacite) {
		this.etat = vivacite.etat;
		this.instructionUtilisatrice = vivacite.instructionUtilisatrice;
	}



	public void faireVivre(InstructionGenerale instruction) {
		if (etat == Etat.Vivant) {
			instructionUtilisatrice = null;
		} else if (etat == Etat.Mort || etat == null) {
			etat = Etat.Vivant;
			instructionUtilisatrice = instruction;
		}
	}


	public void tuer(InstructionAffectation instructionActuelle, DetecteurDeSimplifications detecteur) {
		if (etat == Etat.Mort) {
			detecteur.instructionsAIgnorer.add(instructionActuelle);
		} else {
			etat = Etat.Mort;

			if (instructionUtilisatrice != null) {
				detecteur.instructionsAIgnorer.add(instructionActuelle);
				Utilitaire.Maps.ajouterElementDansListe(detecteur.affectationsInlinables, instructionUtilisatrice,
						instructionActuelle);
			}

			detecteur.noterExpression(instructionActuelle, instructionActuelle.expression);
		}
	}





	public enum Etat {
		Mort,
		Vivant
	}

	public enum Comportement {

	}

}
