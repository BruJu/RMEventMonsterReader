package decrypter;

import java.util.List;

public class ElementDecrypte {
	public final Instruction instruction;
	public final List<String> arguments;
	
	public ElementDecrypte(Instruction instruction, List<String> arguments) {
		this.instruction = instruction;
		this.arguments = arguments;
	}
}
