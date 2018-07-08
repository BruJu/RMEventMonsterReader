package decrypter;

import java.util.ArrayList;
import java.util.List;

class AssociationChaineInstruction {
	final String pattern;
	final Instruction instruction;
	
	AssociationChaineInstruction(Instruction instruction, String pattern) {
		this.pattern = pattern;
		this.instruction = instruction;
	}
	
	
	static List<AssociationChaineInstruction> bookMaker() {
		List<AssociationChaineInstruction> book = new ArrayList<>();
		
		//  <> Change Switch: [410] = ON
		bookMaking(book, Instruction.CHGSWITCH, "<> Change Switch: [_] = _");
		
		return book;
	}
	
	private static List<AssociationChaineInstruction> bookMaking(List<AssociationChaineInstruction> book, Instruction instruction, String pattern) {
		book.add(new AssociationChaineInstruction(instruction, pattern));
		return book;
	}
}
