package monsterlist.manipulation;

import java.util.ArrayList;
import java.util.List;


class ConditionManipulator {
	static <T> List<T> filterList(List<? extends Condition<T>> conditions, List<T> elements) {
		List<T> elementsFiltres = new ArrayList<>();
		
		elements.stream()
				.filter(element -> filter(conditions, element))
				.forEach(elementsFiltres::add);

		return elementsFiltres;
	}
	
	static <T> boolean filter(List<? extends Condition<T>> conditions, T element) {
		for (Condition<T> condition : conditions) {
			if (!condition.filter(element)) {
				return false;
			}
		}
		
		return true;
	}
}
