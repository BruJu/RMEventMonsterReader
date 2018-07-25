package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.filtres;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.FormuleDeDegats;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;

public class MaillonFiltreOperateur implements Maillon {
	private Operator operateurFiltre;

	public MaillonFiltreOperateur(Operator operateurFiltre) {
		this.operateurFiltre = operateurFiltre;
	}

	@Override
	public void traiter(Attaques attaques) {
		attaques.getAttaques().forEach(action -> action.faireOperation(degats -> {
			List<FormuleDeDegats> nouvelleListe = new ArrayList<>();
			if (degats.getOperator() != operateurFiltre) {
				nouvelleListe.add(degats);
			}

			return nouvelleListe;
		}));
	}

}
