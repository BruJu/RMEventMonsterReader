package fr.bruju.rmeventreader.actionmakers.executeur.modele;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public class VariablePlage implements ValeurGauche {
	public final int idVariableMin;
	public final int idVariableMax;
	
	public VariablePlage(int idVariableMin, int idVariableMax) {
		this.idVariableMin = idVariableMin;
		this.idVariableMax = idVariableMax;
	}
	
	public List<Variable> getList() {
		List<Variable> variables = new ArrayList<>(idVariableMax - idVariableMin + 1);
		
		for (int i = idVariableMin ; i <= idVariableMax ; i++) {
			variables.add(new Variable(i));
		}
		
		return variables;
	}

	@Override
	public <T> T appliquerG(Function<Variable, T> variable, Function<VariablePlage, T> plage,
			Function<Pointeur, T> pointeur) {
		return plage == null ? null : plage.apply(this);
	}
	
}
