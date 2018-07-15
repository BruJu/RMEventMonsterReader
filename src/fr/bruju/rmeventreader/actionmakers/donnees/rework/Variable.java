package fr.bruju.rmeventreader.actionmakers.donnees.rework;

public class Variable implements LeftValue {
	private int idVariable;
	
	public Variable(int idVariable) {
		this.idVariable = idVariable;
	}
	
	public int get() {
		return idVariable;
	}
}
