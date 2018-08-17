package fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import fr.bruju.rmeventreader.dictionnaires.header.Instruction;
import fr.bruju.rmeventreader.dictionnaires.header.Monteur;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

public class Instr<K extends Monteur<?>> implements Traitement<K> {
	private Instruction instruction;
	private BiConsumer<K, Instruction> operationDeMontage;
	
	public Instr(BiConsumer<K, Instruction> operationDeMontage) {
		this.operationDeMontage = operationDeMontage;
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
	public void appliquer(K monteur) {
		operationDeMontage.accept(monteur, instruction);
	}
}
