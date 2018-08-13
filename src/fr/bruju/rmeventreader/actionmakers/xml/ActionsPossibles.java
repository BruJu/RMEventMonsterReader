package fr.bruju.rmeventreader.actionmakers.xml;

import java.util.HashMap;
import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;

public class ActionsPossibles {
	public static interface Action {
		public void exec(ActionMaker actionMaker, String string, int[] parameters);
	}
	
	public static Map<Long, Action> remplirActions() {
		return new ActionsPossibles().getMap();
	}
	
	private Map<Long, Action> getMap() {
		Map<Long, Action> actions = new HashMap<>();
		
		actions.put(10110L, this::showMessage);
		
		return actions;
	}
	
	
	private void showMessage(ActionMaker actionMaker, String string, int[] parameters) {
		actionMaker.notImplementedFeature("Show Message : " + string);
	}
	
	
}
