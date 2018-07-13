package monsterlist.autotraitement;

import java.io.File;
import java.io.IOException;

import actionner.ActionMaker;
import actionner.ActionMakerWithConditionalInterest;
import actionner.ConditionalActionMaker;
import actionner.Interpreter;

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
