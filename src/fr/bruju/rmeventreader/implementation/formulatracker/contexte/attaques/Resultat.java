package fr.bruju.rmeventreader.implementation.formulatracker.contexte.attaques;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.implementation.formulatracker.contexte.personnage.Statistique;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.formule.FormuleDeDegats;

public class Resultat {
	public Map<Statistique, List<FormuleDeDegats>> map;
	
	public Resultat() {
		map = new HashMap<>();
	}

	public void integrer(Statistique stat, List<FormuleDeDegats> formules) {
		map.put(stat, formules);
	}
}
