package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression;

public class CaseMemoire {
	public final int numeroCase;

	public CaseMemoire(int numeroCase) {
		this.numeroCase = numeroCase;
	}
	
	public VariableInstanciee premiereInstance() {
		return new VariableInstanciee(this);
	}
	
	

}
