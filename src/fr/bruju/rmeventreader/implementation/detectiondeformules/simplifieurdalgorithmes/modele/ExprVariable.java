package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele;

import fr.bruju.rmdechiffreur.modele.Variable;

public class ExprVariable implements Expression {
	public final int numeroVariable;




	public ExprVariable(int numeroVariable) {
		this.numeroVariable = numeroVariable;
	}

	public ExprVariable(Variable valeurDroite) {
		numeroVariable = valeurDroite.idVariable;
	}




	@Override
	public String getString() {
		return "V[" + String.format("%04d", numeroVariable) + "]";
	}

}
