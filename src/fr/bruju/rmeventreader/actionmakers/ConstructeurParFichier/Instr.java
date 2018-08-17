package fr.bruju.rmeventreader.actionmakers.ConstructeurParFichier;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.dictionnaires.header.Instruction;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

public class Instr implements Traitement {
	private Instruction instruction;
	
	public Instr() {
	}
	
	@Override
	public Avancement traiter(String ligne) {
		String[] d = ligne.split(" ");
		
		int code = Integer.parseInt(d[0]);
		String string = ligne.substring(ligne.indexOf(";") + 2 , ligne.length());
		
		List<Integer> params = new ArrayList<>();
		
		for (int i = 1 ; i != d.length ; i++) {
			String s = d[i];
			
			if (s.equals(";"))
				break;
			
			params.add(Integer.parseInt(s));
		}
		
		instruction = new Instruction(code, string, Utilitaire.toArrayInt(params));
		
		return Avancement.Suivant;
	}

	@Override
	public Instruction resultat() {
		return instruction;
	}
}
