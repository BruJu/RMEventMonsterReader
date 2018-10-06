package fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.operateurdesimplification.extension;

import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.etendu.E_Borne;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.etendu.E_Entre;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.visiteur.ConstructeurDeComposantsRecursif;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.operateurdesimplification.Maillon;

/**
 * Maillon fusionannt les successions de min et de max par un encadrement (entre).
 * <p>
 * Par exemple min(max(a;b);c) devient entre (c;a;b). 
 * 
 * @author Bruju
 *
 */
public class Encadrer extends ConstructeurDeComposantsRecursif implements Maillon {
	// =================================================================================================================
	// =================================================================================================================
	// =================================================================================================================
	//  - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON -

	@Override
	public void traiter(Attaques attaques) {
		attaques.transformerComposants(this::traiter);
	}

	// =================================================================================================================
	// =================================================================================================================
	// =================================================================================================================
	//            - CONSTRUCTEUR DE COMPOSANTS - CONSTRUCTEUR DE COMPOSANTS - CONSTRUCTEUR DE COMPOSANTS -

	@Override
	protected Composant modifier(E_Borne borne) {
		if (borne.valeur instanceof E_Borne) {
			E_Borne autreBorne = (E_Borne) (borne.valeur);
			
			if (autreBorne.estBorneSup != borne.estBorneSup) {
				if (borne.estBorneSup) {
					return new E_Entre(autreBorne.borne, autreBorne.valeur, borne.borne);
				} else {
					return new E_Entre(borne.borne, autreBorne.valeur, autreBorne.borne);
				}
			}
		}
		
		return borne;
	}
}
