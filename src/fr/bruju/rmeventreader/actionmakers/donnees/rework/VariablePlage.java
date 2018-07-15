package fr.bruju.rmeventreader.actionmakers.donnees.rework;

import java.util.ArrayList;
import java.util.List;

public class VariablePlage implements LeftValue {
	private int idVariableMin;
	private int idVariableMax;
	
	public VariablePlage(int idVariableMin, int idVariableMax) {
		this.idVariableMin = idVariableMin;
		this.idVariableMax = idVariableMax;
	}
	
	public int getMin() {
		return idVariableMin;
	}
	
	public int getMax() {
		return idVariableMax;
	}
	
	public List<Variable> getList() {
		List<Variable> variables = new ArrayList<>(idVariableMax - idVariableMin + 1);
		
		for (int i = idVariableMin ; i <= idVariableMax ; i++) {
			variables.add(new Variable(i));
		}
		
		return variables;
	}
	
}
