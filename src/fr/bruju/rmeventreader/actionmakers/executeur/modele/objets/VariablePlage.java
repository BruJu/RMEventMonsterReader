package fr.bruju.rmeventreader.actionmakers.executeur.modele.objets;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurGauche;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurValeurGauche;

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
	public <T> T accept(VisiteurValeurGauche<T> visiteurValeurGauche)  {
		return visiteurValeurGauche.visit(this);
	}
}
