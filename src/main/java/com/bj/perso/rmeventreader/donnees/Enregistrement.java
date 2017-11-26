package com.bj.perso.rmeventreader.donnees;

import com.bj.perso.rmeventreader.modificateur.Modificateur;

public interface Enregistrement {
	
	
	public SousEnregistrement getSousEnregistrement(int id);
	
	// Pour chaque instruction :
	// public void affecter(Modificateur modificateur, InstructionRM instrution);
	
}
