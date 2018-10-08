package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression;

import fr.bruju.rmdechiffreur.modele.Variable;

import static fr.bruju.rmeventreader.ProjetS.PROJET;

public class ExprVariable implements Expression {
	public final int numeroVariable;
	public final int version = 0;



	public ExprVariable(int numeroVariable) {
		this.numeroVariable = numeroVariable;
	}

	public ExprVariable(Variable valeurDroite) {
		numeroVariable = valeurDroite.idVariable;
	}




	@Override
	public String getString() {
		if (numeroVariable < 0) {
			return "S[" + String.format("%04d", -numeroVariable) + ":" + PROJET.extraireInterrupteur(-numeroVariable).trim()  + "]";
		}
		
		return "V[" + String.format("%04d", numeroVariable) + ":" + PROJET.extraireVariable(numeroVariable).trim() + "]";
	}

}
