package fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.formulededegats;

import java.util.ArrayList;
import java.util.List;

import java.util.Objects;
import java.util.stream.Collectors;

public class GroupeDeConditions {
	public final List<ConditionAffichable> conditions;	
	
	public GroupeDeConditions(List<ConditionAffichable> conditions) {
		this.conditions = conditions;
	}

	public GroupeDeConditions(ConditionAffichable condition) {
		this.conditions = new ArrayList<>(1);
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
			return Objects.deepEquals(this.conditions, that.conditions);
		}
		return false;
	}

	@Override
	public String toString() {
		return conditions.stream().map(c -> c.getString()).collect(Collectors.joining(","));
	}
	
	
}
