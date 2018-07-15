package fr.bruju.rmeventreader.actionmakers.donnees;

public class Variable implements LeftValue, RightValue {
	private int idVariable;
	
	public Variable(int idVariable) {
		this.idVariable = idVariable;
	}
	
	public int get() {
		return idVariable;
	}
}
