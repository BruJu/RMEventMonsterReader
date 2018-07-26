package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.filtres;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;

public class MaillonFiltreAttaque implements Maillon {
	String attaqueConservee;
	
	public MaillonFiltreAttaque(String string) {
		this.attaqueConservee = string;
	}

	@Override
	public void traiter(Attaques attaques) {
		attaques.filterAttaques(attaque -> attaque.nom.equals(attaqueConservee));
	}

}
