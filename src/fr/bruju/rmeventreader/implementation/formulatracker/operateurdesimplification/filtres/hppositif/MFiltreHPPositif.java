package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.filtres.hppositif;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.contexte.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.exploitation.Maillon;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.FormuleDeDegats;

public class MFiltreHPPositif implements Maillon {

	@Override
	public void traiter(Attaques attaques) {
		FiltreHPPositifs filtre = new FiltreHPPositifs();
		
		attaques.apply(formuleDeDegats -> {
			List<Condition> conditions = formuleDeDegats.conditions;
			
			for (Condition condition : conditions) {
				boolean b = filtre.traiter(condition) != null;
				
				if (!b) {
					return new FormuleDeDegats(Operator.DIFFERENT, new ArrayList<>(), new VConstante(0));
				}
			}
			
			return new FormuleDeDegats(formuleDeDegats.operator, conditions, (Valeur) filtre.traiter(formuleDeDegats.formule));
		}
		
				
				);
		
	}

}
