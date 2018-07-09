package decrypter;

import java.util.List;

import decrypter.convertisseurs.Action;

public class ElementDecrypte {
	public final Action instruction;
	public final List<String> arguments;
	
	public ElementDecrypte(Action instruction, List<String> arguments) {
		this.instruction = instruction;
		this.arguments = arguments;
	}
}
