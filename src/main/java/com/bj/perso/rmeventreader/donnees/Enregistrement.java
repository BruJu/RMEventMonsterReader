package com.bj.perso.rmeventreader.donnees;


public class Enregistrement {
	private int id;
	
	public Enregistrement(int id) {
		this.id = id;
	}
	
	public SousEnregistrement getSousEnregistrement(int id) {
		return null;
	}

	public int compare(Enregistrement e2) {
		if (this.id < e2.id)
			return -1;
		else if (this.id == e2.id)
			return 0;
		else
			return 1;
	}

	public boolean fuse(Enregistrement suivant) {
		if (this.getId() != suivant.getId())
			return false;
		
		// TODO
		
		return true;
	}

	private int getId() {
		return id;
	}
	
	// Pour chaque instruction :
	// public void affecter(Modificateur modificateur, InstructionRM instrution);
	
}
