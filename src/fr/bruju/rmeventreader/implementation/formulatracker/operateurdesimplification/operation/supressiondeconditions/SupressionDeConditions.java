package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.supressiondeconditions;

import java.util.stream.Collectors;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.contexte.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.exploitation.Maillon;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.FormuleDeDegats;

public class SupressionDeConditions implements Maillon {

	@Override
	public void traiter(Attaques attaques) {
		Supresseur suppresseur = new Supresseur();
		
		attaques.apply(formule -> new FormuleDeDegats(formule.operator,
				formule.conditions.stream()
						.map(c -> suppresseur.soumettre(c, "ressources/formulatracker/Suppresseur.txt"))
						.filter(c -> !(c.equals(CFixe.get(true))))
						.collect(Collectors.toList()),
				formule.formule));
	}
}
