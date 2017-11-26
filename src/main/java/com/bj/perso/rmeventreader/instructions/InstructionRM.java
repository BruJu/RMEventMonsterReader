package com.bj.perso.rmeventreader.instructions;

import java.util.List;

import com.bj.perso.rmeventreader.donnees.Enregistrement;
import com.bj.perso.rmeventreader.modificateur.Modificateur;

public interface InstructionRM {
	
	
	public void affecter(Modificateur modificateur, List<Enregistrement> enregistrements);
	
	public InstructionRM avancer(List<InstructionRM> instructions);
	
	
}
