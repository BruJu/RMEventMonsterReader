package com.bj.perso.rmeventreader.instructions;

import java.util.List;

import com.bj.perso.rmeventreader.donnees.Enregistrement;
import com.bj.perso.rmeventreader.modificateur.Modificateur;

public class InstructionVoid implements InstructionRM {

	@Override
	public void affecter(Modificateur modificateur, List<Enregistrement> enregistrements) {

	}

	@Override
	public InstructionRM avancer(List<InstructionRM> instructions) {
		int me = instructions.indexOf(this);
		InstructionRM nextInstruction = instructions.get(me + 1);
		return nextInstruction;
	}

}
