package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.filtres;

import java.util.stream.Collectors;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;

public class MaillonFiltreAttaque implements Maillon {
	String attaqueConservee;
	
	public MaillonFiltreAttaque(String string) {
		this.attaqueConservee = string;
	}

	@Override
	public void traiter(Attaques attaques) {
		attaques.liste = attaques.getAttaques().stream().filter(attaque -> attaque.getNom().equals(attaqueConservee))
				.collect(Collectors.toList());
	}

}
