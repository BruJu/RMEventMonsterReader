package fr.bruju.rmeventreader.implementation.formulareader;

import java.io.File;

import fr.bruju.rmeventreader.implementation.monsterlist.autotraitement.AutoActionMaker;

public class FormulaMain {

	public static void main_(String[] args) {
		
		
		
		File file = new File("ressources/Attaques");
		
		for (String fichiersTexte : file.list()) {

			FormulaCalculator calc = new FormulaCalculator();
			new AutoActionMaker(calc, "ressources/Attaques/" + fichiersTexte).faire();
			
			
			String ccc = null;
			
			if (calc.getSortie() != null) {
				if (ccc == null || calc.getSortie().getStringMin().contains(ccc)) {
					System.out.print("--" +  fichiersTexte.substring(0, fichiersTexte.length() - 4) + " = ");
					System.out.println(calc.getSortie().getStringUnique());
				}
			} else {
				System.out.println("--" + fichiersTexte.substring(0, fichiersTexte.length() - 4) + " : Pas de sortie");
			}
			
			
		}
		
		
		
		
		

		
	}

}
