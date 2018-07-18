package fr.bruju.rmeventreader.implementation.monsterlist.manipulation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MetaStack<T> {
	private List<Condition<T>> elements;
	
	public MetaStack() {
		elements = new ArrayList<>();
	}
	
	public void push(Condition<T> element) {
		elements.add(element);
	}
	
	public void pop() {
		elements.remove(elements.size() - 1);
	}
	
	public void revertTop() {
		elements.get(elements.size() - 1).revert();
	}
	
	public Collection<T> filter(Collection<T> listeAFiltrer) {
		return ConditionManipulator.<T>filterList(elements, listeAFiltrer);
	}	
}
