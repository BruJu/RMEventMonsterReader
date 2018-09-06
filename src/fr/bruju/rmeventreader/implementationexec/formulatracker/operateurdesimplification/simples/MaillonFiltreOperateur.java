package fr.bruju.rmeventreader.implementationexec.formulatracker.operateurdesimplification.simples;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.OpMathematique;
import fr.bruju.rmeventreader.implementationexec.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementationexec.formulatracker.operateurdesimplification.Maillon;

/**
 * Maillon supprimant les formules appliquant l'opérateur donné
 * 
 * @author Bruju
 *
 */
public class MaillonFiltreOperateur implements Maillon {
	/** Opérateur à retirer */
	private OpMathematique operateurFiltre;

	/**
	 * Construit un filtre pour retirer les formules appliquant l'opérateur donné
	 * @param operateurFiltre L'opérateur dont on souhaite éliminé les formules l'utilisant
	 */
	public MaillonFiltreOperateur(OpMathematique operateurFiltre) {
		this.operateurFiltre = operateurFiltre;
	}

	@Override
	public void traiter(Attaques attaques) {
		attaques.filterKeys(modifStat -> modifStat.operateur != operateurFiltre);
	}

}
