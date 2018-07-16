package fr.bruju.rmeventreader.implementation.formulareader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.implementation.formulareader.actionmaker.Etat;
import fr.bruju.rmeventreader.implementation.formulareader.actionmaker.FormulaCalculator;
import fr.bruju.rmeventreader.implementation.formulareader.formule.Valeur;
import fr.bruju.rmeventreader.implementation.formulareader.model.CreateurPersonnage;
import fr.bruju.rmeventreader.implementation.formulareader.model.Personnage;
import fr.bruju.rmeventreader.implementation.monsterlist.autotraitement.AutoActionMaker;
import fr.bruju.rmeventreader.utilitaire.Pair;

public class FormulaMain {

	public static void main_(String[] args) {

		List<Personnage> persos = CreateurPersonnage.creerTousLesPersonnages();
		Map<Personnage, List<Pair<String, Valeur>>> map = new HashMap<>();
		
		for (Personnage perso : persos) {
			map.put(perso, new ArrayList<>());
		}

		File file = new File("ressources/Attaques");

		for (String fichiersTexte : file.list()) {

			FormulaCalculator calc = new FormulaCalculator();
			new AutoActionMaker(calc, "ressources/Attaques/" + fichiersTexte).faire();

			List<Valeur> val = calc.getSortie();
			String nomAttaque = fichiersTexte.substring(0, fichiersTexte.length() - 4);

			if (val.isEmpty()) {
				continue;
			}

			persos.forEach(perso -> val.forEach(valeur -> {
				if (valeur == null)
					return;

				if (valeur.getString().contains(perso.getNom())) {
					map.get(perso).add(new Pair<>(nomAttaque, valeur));
				}

			}

			)

			);

		}

		for (Personnage perso : persos) {
			if (perso.getNom().contains("Monstre"))
				continue;
			if (perso.getNom().contains("Membre"))
				continue;

			System.out.println("===== " + perso.getNom() + "=====");

			map.get(perso)
					.forEach(element -> System.out.println(element.getLeft() + " = " + element.getRight().getString()));
		}

		System.out.println();
		System.out.println();
		System.out.println("Variable crees : ");

		for (Integer idVar : Etat.idVariablesCrees) {

			System.out.print(idVar + " ");
		}
		System.out.println();
		
		
		

	}

}
