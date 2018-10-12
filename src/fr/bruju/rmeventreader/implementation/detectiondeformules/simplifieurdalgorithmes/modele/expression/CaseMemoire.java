package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression;

public class CaseMemoire {
	public final int numeroCase;
	private VariableInstanciee premiereInstance;

	/*
	 * 
	public CaseMemoire(int numeroCase) {
		this.numeroCase = numeroCase;
		premiereInstance = new VariableInstanciee(this);
	}
	*/
	
	public CaseMemoire(int numeroCase, Integer valeurInitiale) {
		this.numeroCase = numeroCase;
		premiereInstance = new VariableInstanciee(this, valeurInitiale);
	}
	
	
	public VariableInstanciee premiereInstance() {
		return premiereInstance;
	}
	
	

}
