package fr.bruju.rmeventreader.implementation.formulareader;

import java.io.File;

import fr.bruju.rmeventreader.implementation.monsterlist.autotraitement.AutoActionMaker;

public class FormulaMain {

	public static void main_(String[] args) {
		
		
		
		File file = new File("ressources/Attaques");
		
		for (String fichiersTexte : file.list()) {

			FormulaCalculator calc = new FormulaCalculator();
			new AutoActionMaker(calc, "ressources/Attaques/" + fichiersTexte).faire();
			
			System.out.println("--" + fichiersTexte);
			
			if (calc.getSortie() != null) {
				System.out.println(calc.getSortie().getStringMin());
				System.out.println(calc.getSortie().getStringMax());
			} else {
				System.out.println("Pas de formule");
			}
			
			
		}
		
		
		
		
		

		
	}

}
