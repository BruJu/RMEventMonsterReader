package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.extension;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.etendu.E_Borne;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.etendu.E_Entre;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.ConstructeurDeComposantR;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;

public class Encadrer extends ConstructeurDeComposantR implements Maillon {

	@Override
	public void traiter(Attaques attaques) {
		attaques.transformerComposants(this::traiter);
	}

	@Override
	protected Composant traiter(E_Borne borne) {
		
		borne = new E_Borne((Valeur) traiter(borne.valeur), (Valeur) traiter(borne.borne), borne.estBorneSup);
		
		
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
		
		
		return super.traiter(borne);
	}
	
	
	

}
