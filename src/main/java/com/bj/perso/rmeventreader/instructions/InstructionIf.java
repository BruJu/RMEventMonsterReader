package com.bj.perso.rmeventreader.instructions;

import java.util.List;

import com.bj.perso.rmeventreader.donnees.Catalogue;
import com.bj.perso.rmeventreader.donnees.Condition;
import com.bj.perso.rmeventreader.donnees.Enregistrement;
import com.bj.perso.rmeventreader.modificateur.Modificateur;

public class InstructionIf implements InstructionRM {
	private Condition condition;
	
	@Override
	public void affecter(Modificateur modificateur, List<Enregistrement> enregistrements) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public InstructionRM avancer(List<InstructionRM> instructions) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean tester(Enregistrement enregistrement) {
		return true;
	}

	public void repart(Catalogue catalogueIf, Catalogue catalogueElse, Enregistrement enregistrement) {
		condition.add(catalogueIf, catalogueElse, enregistrement);
	}

}
