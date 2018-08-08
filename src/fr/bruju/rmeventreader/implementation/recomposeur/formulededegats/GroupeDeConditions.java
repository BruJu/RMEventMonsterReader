package fr.bruju.rmeventreader.implementation.recomposeur.formulededegats;

import java.util.List;

import java.util.Objects;


public class GroupeDeConditions {
	public final List<ConditionAffichable> conditions;	
	
	public GroupeDeConditions(List<ConditionAffichable> conditions) {
		this.conditions = conditions;
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
