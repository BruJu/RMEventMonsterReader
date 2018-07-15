package fr.bruju.rmeventreader.implementation.formulareader;

import fr.bruju.rmeventreader.implementation.monsterlist.autotraitement.AutoActionMaker;

public class FormulaMain {

	public static void main_(String[] args) {
		
		FormulaCalculator calc = new FormulaCalculator();
		
		new AutoActionMaker(calc, "ressources/Attaques/Irzy_LumSacree.txt").faire();
		
		System.out.println(calc.getSortie().getStringMin());
		System.out.println(calc.getSortie().getStringMax());
		
	}

}
