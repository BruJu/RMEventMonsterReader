package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele;

public class Variable implements Expression {
	public final int numeroVariable;




	public Variable(int numeroVariable) {
		this.numeroVariable = numeroVariable;
	}




	@Override
	public String getString() {
		return "V[" + String.format("%04d", numeroVariable) + "]";
	}

}
