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
	/** Opérateur à retirer */
	private Operator operateurFiltre;

	/**
	 * Construit un filtre pour retirer les formules appliquant l'opérateur donné
	 * @param operateurFiltre L'opérateur dont on souhaite éliminé les formules l'utilisant
	 */
	public MaillonFiltreOperateur(Operator operateurFiltre) {
		this.operateurFiltre = operateurFiltre;
	}

	@Override
	public void traiter(Attaques attaques) {
		attaques.filterKeys(modifStat -> modifStat.operateur != operateurFiltre);
	}

}
