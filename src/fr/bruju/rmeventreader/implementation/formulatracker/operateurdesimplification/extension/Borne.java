package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.extension;

import java.util.Objects;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.etendu.E_Borne;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VAleatoire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.ConstructeurDeComposantsRecursif;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;

/**
 * Maillon transformant les ternaires du type si a > b alors b sinon a en max / min.
 * 
 * @author Bruju
 *
 */
public class Borne extends ConstructeurDeComposantsRecursif implements Maillon {
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
	protected Composant modifier(VTernaire variableTernaire) {
		if (!(variableTernaire.condition instanceof CVariable)) {
			return variableTernaire;
		}

		CVariable cv = (CVariable) variableTernaire.condition;
		Valeur vrai = variableTernaire.siVrai;
		Valeur faux = variableTernaire.siFaux;
		
		if (cv.operateur == Operator.INF || cv.operateur == Operator.INFEGAL) {
			// max
			if (sontSimilaire(cv.gauche, vrai) && sontSimilaire(cv.droite, faux)) {
				return new E_Borne(vrai, faux, true); 
			}
			
			// min
			if (sontSimilaire(cv.droite, vrai) && sontSimilaire(cv.gauche, faux)) {
				return new E_Borne(faux, vrai, false); 
			}
		}
		
		if (cv.operateur == Operator.SUP || cv.operateur == Operator.SUPEGAL) {
			// max
			if (sontSimilaire(cv.droite, vrai) && sontSimilaire(cv.gauche, faux)) {
				return new E_Borne(faux, vrai, true); 
			}
			
			// min
			if (sontSimilaire(cv.gauche, vrai) && sontSimilaire(cv.droite, faux)) {
				return new E_Borne(vrai, faux, false); 
			}
		}

		return variableTernaire;
	}
	
	public boolean sontSimilaire(Valeur condition, Valeur retour) {
		if (Objects.equals(condition, retour))
			return true;
		
		
		// TODO : faire un traitement plus exact
		if (retour instanceof VAleatoire && condition instanceof VConstante) {
			return true;
		}
		
		return false;
	}


}