package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.ConstructeurDeComposantsRecursif;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;

public class MaillonRetirerSiInutile extends ConstructeurDeComposantsRecursif implements Maillon {

	@Override
	public void traiter(Attaques attaques) {
		attaques.transformerComposants(this::traiter);
	}

	@Override
	protected Composant modifier(BTernaire boutonTernaire) {
		Bouton bf = boutonTernaire.siFaux;
		Bouton bv = boutonTernaire.siVrai;
		
		if (bv.equals(bf)) {
			return bf;
		} else {
			return boutonTernaire;
		}
	}

	@Override
	protected Composant modifier(VTernaire variableTernaire) {
		Valeur bf = (variableTernaire.siFaux);
		Valeur bv = (variableTernaire.siVrai);
		
		if (bv.equals(bf)) {
			return bf;
		} else {
			return variableTernaire;
		}
	}

	
	
	
}
