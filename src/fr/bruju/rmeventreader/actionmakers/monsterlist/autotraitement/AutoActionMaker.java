package fr.bruju.rmeventreader.actionmakers.monsterlist.autotraitement;

import java.io.File;
import java.io.IOException;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;
import fr.bruju.rmeventreader.actionmakers.actionner.ActionMakerWithConditionalInterest;
import fr.bruju.rmeventreader.actionmakers.actionner.ConditionalActionMaker;
import fr.bruju.rmeventreader.actionmakers.actionner.Interpreter;

public class AutoActionMaker implements ActionAutomatique {
	private ActionMakerWithConditionalInterest condActionMaker;
	private String filename;
	
	public AutoActionMaker(ActionMakerWithConditionalInterest condActionMaker, String filename) {
		this.condActionMaker = condActionMaker;
		this.filename = filename;
	}
	
	@Override
	public void faire() {
		ActionMaker conditionalActionMaker = new ConditionalActionMaker(condActionMaker);
		
		Interpreter interpreter = new Interpreter(conditionalActionMaker);
		
		try {
			interpreter.inputFile(new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
