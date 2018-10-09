package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression;

public class CaseMemoire {
	public final int numeroCase;
	private VariableInstanciee premiereInstance;

	public CaseMemoire(int numeroCase) {
		this.numeroCase = numeroCase;
		premiereInstance = new VariableInstanciee(this);
	}
	
	public VariableInstanciee premiereInstance() {
		return premiereInstance;
	}
	
	

}
