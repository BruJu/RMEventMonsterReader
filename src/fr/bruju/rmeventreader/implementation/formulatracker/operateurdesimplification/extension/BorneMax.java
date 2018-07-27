package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.extension;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.etendu.E_BorneSuperieure;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.ConstructeurDeComposantR;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;

public class BorneMax extends ConstructeurDeComposantR implements Maillon {

	@Override
	public void traiter(Attaques attaques) {
		attaques.transformerComposants(this::traiter);
	}

	@Override
	protected Composant traiter(VTernaire variableTernaire) {

		Condition c = (Condition) traiter(variableTernaire.condition);
		Valeur vrai = (Valeur) traiter(variableTernaire.siVrai);
		Valeur faux = (Valeur) traiter(variableTernaire.siFaux);
		
		if (!(c instanceof CVariable)) {
			return super.traiter(variableTernaire); 
		}
		
		CVariable cv = (CVariable) c; 
		
		// if (a < b) then a else b
		if ( (cv.operateur == Operator.INF || cv.operateur == Operator.INFEGAL)
				&& vrai.equals(cv.gauche) && faux.equals(cv.droite)) {
			return new E_BorneSuperieure(vrai, faux);
		}

		// if (a > b) then b else a
		if ( (cv.operateur == Operator.SUP || cv.operateur == Operator.SUPEGAL)
				&& vrai.equals(cv.droite) && faux.equals(cv.gauche)) {
			return new E_BorneSuperieure(faux, vrai);
		}
		
		return super.traiter(variableTernaire);
	}


}