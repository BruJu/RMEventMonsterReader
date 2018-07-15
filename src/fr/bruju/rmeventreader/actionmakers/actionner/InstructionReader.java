package fr.bruju.rmeventreader.actionmakers.actionner;

import fr.bruju.rmeventreader.actionmakers.decrypter.Decrypter;
import fr.bruju.rmeventreader.filereader.ActionOnLine;

public class InstructionReader implements ActionOnLine {
	private Decrypter decrypter;
	
	public InstructionReader(Decrypter decrypter) {
		this.decrypter = decrypter;
	}

	private int statut = 0;		// 0 = inconnu ; 1 = skip to - SCRIPT - ; 2 =  - SCRIPT - found
	
	@Override
	public void read(String line) {
		if (statut == 0) {
			if (line.equals("--- EVENT ---")) {
				statut = 1;
			} else {
				statut = 2;
			}
		}
		
		if (statut == 1) {
			if (line.equals("- SCRIPT -")) {
				statut = 2;
			}
		}
		
		if (statut == 2) {
			decrypter.decript(line);
		}
	}

}
