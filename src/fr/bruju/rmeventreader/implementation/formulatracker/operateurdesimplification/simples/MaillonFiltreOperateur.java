package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.simples;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;

/**
 * Maillon supprimant les formules appliquant l'opérateur donné
 * 
 * @author Bruju
 *
 */
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
