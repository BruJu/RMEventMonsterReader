package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.injection.desinjectioncomposant;

import java.util.Collection;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.contexte.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.exploitation.Maillon;

public class MDesinjecteur implements Maillon {

	@Override
	public void traiter(Attaques attaques) {
		Desinjection desinjection = new Desinjection();
		desinjection.remplirAvecFichier("ressources/formulatracker/desinjection.txt");
		Desinjecteur desinjecteur = new Desinjecteur();

		attaques.modifierFormules((stat, formule) -> Attaques.transformationDeFormule(formule,
				composant -> desinjecteur.desinjecter(composant, desinjection.getConditions(stat.possesseur.getNom())))
		);
	}

}
