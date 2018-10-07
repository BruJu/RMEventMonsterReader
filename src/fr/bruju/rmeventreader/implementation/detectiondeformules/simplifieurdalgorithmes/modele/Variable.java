package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele;

public class Variable implements Expression {
	public final String nomVariable;
	
	public Variable(String nomVariable) {
		this.nomVariable = nomVariable;
	}




	@Override
	public String getString() {
		return nomVariable;
	}

}
