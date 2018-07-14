package monsterlist.actionmaker;

import java.util.List;

import actionner.ActionMakerWithConditionalInterest;
import monsterlist.manipulation.MetaStack;

public abstract class StackedActionMaker<T> implements ActionMakerWithConditionalInterest {

	protected MetaStack<T> conditions = new MetaStack<>();
	
	public List<T> getElementsFiltres() {
		return conditions.filter(getAllElements());
	}
	
	protected abstract List<T> getAllElements();

	@Override
	public void condElse() {
		conditions.revertTop();
	}

	@Override
	public void condEnd() {
		conditions.pop();
	}

}
