package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.filtres;



import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;

public class MaillonFiltreOperateur implements Maillon {
	private Operator operateurFiltre;

	public MaillonFiltreOperateur(Operator operateurFiltre) {
		this.operateurFiltre = operateurFiltre;
	}

	@Override
	public void traiter(Attaques attaques) {
		attaques.filterKeys(modifStat -> modifStat.operateur != operateurFiltre);
	}

}
