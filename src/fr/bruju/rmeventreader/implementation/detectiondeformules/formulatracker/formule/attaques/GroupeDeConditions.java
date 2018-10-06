package fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.formule.attaques;

import java.util.ArrayList;
import java.util.List;

import java.util.Objects;

public class GroupeDeConditions {
	public final List<ConditionAffichable> conditions;
	
	public GroupeDeConditions() {
		conditions = new ArrayList<>();
	}
	
	public GroupeDeConditions(GroupeDeConditions groupe, ConditionAffichable condition) {
		conditions = new ArrayList<>(groupe.conditions);
		
		if (condition.condition != null)
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
