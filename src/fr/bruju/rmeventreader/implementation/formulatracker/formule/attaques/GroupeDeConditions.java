package fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import java.util.Objects;

public class GroupeDeConditions {
	public final List<Condition> conditions;
	
	public GroupeDeConditions() {
		conditions = new ArrayList<>();
	}
	
	public GroupeDeConditions(GroupeDeConditions groupe, Condition condition) {
		conditions = new ArrayList<>(groupe.conditions);
		
		if (condition != null)
			conditions.add(condition);
	}
	
	

	@Override
	public int hashCode() {
		return Objects.hashCode(conditions);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof GroupeDeConditions) {
			GroupeDeConditions that = (GroupeDeConditions) object;
			return Objects.equals(this.conditions, that.conditions);
		}
		return false;
	}
	
	
}
