package decrypter;


import actionner.ActionMaker;

public class Decrypter {
	private ActionMaker actionMaker;
	private Recognizer recognizer;

	public Decrypter(ActionMaker actionMaker) {
		this.actionMaker = actionMaker;
		this.recognizer = new Recognizer();
	}

	public void decript(String line) {
		ElementDecrypte elementDecrypte = recognizer.recognize(line);
		
		elementDecrypte.instruction.faire(actionMaker, elementDecrypte.arguments);
	}
}
