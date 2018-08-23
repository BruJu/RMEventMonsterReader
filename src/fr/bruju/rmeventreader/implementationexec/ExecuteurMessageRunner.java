package fr.bruju.rmeventreader.implementationexec;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.DechiffreurInstructions;
import fr.bruju.rmeventreader.dictionnaires.header.Instruction;
import fr.bruju.rmeventreader.dictionnaires.liblcfreader.LecteurDeCache;

public class ExecuteurMessageRunner implements Runnable {

	@Override
	public void run() {
		
		List<Instruction> instructions = LecteurDeCache.chargerInstructions(1, 3, 1);
		
		Printer printer = new Printer();
		DechiffreurInstructions dechiffreur = new DechiffreurInstructions(printer);
		
		dechiffreur.executer(instructions);
		
		System.out.print(printer.get());
		
		
	}

}
