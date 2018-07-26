package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.filtres;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.FormuleDeDegats;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.ModifStat;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;

public class MaillonFiltreOperateur implements Maillon {
	private Operator operateurFiltre;

	public MaillonFiltreOperateur(Operator operateurFiltre) {
		this.operateurFiltre = operateurFiltre;
	}

	@Override
	public void traiter(Attaques attaques) {
		attaques.liste.forEach(attaque -> {
			Map<ModifStat, List<FormuleDeDegats>> map = new HashMap<>();
			attaque.resultat.entrySet().stream()
							.filter(entry -> entry.getKey().operateur != operateurFiltre)
							.forEach(entry -> map.put(entry.getKey(), entry.getValue()));
			attaque.resultat = map;
		});
	}

}
