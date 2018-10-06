package fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.operateurdesimplification.inclusion;

import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.formule.attaques.FormuleDeDegats;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.operateurdesimplification.Maillon;

/**
 * Résout les conditions dans les formules en utilisant les connaissances apportées par les préconditions. Ce procédé
 * est désigné comme étant l'intégration.
 * 
 * @author Bruju
 *
 */
public class MaillonIntegration implements Maillon {
	@Override
	public void traiter(Attaques attaques) {
		attaques.transformerFormules(this::inclusionGenerale);
	}
	
	/**
	 * Converti la formule pour intégrer les conditions
	 * @param formuleBase La formule de départ
	 * @return La formule avec les conditions intégrées
	 */
	public FormuleDeDegats inclusionGenerale(FormuleDeDegats formuleBase) {
		IntegreurGeneral integreur = new IntegreurGeneral();
		return integreur.integrer(formuleBase);
	}
}
