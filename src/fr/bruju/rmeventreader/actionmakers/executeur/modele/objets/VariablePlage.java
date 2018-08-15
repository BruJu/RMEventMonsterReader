package fr.bruju.rmeventreader.actionmakers.executeur.modele.objets;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.Fonction;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurGauche;

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
	public <T> T execVG(Fonction<Variable, T> variable, Fonction<VariablePlage, T> plage,
			Fonction<Pointeur, T> pointeur) throws ObjetNonSupporte {
		return plage.apply(this);
	}
}
