package fr.bruju.rmeventreader.implementationexec.formulatracker.operateurdesimplification.simples;

import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.bouton.BTernaire;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.bouton.Bouton;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.valeur.VTernaire;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.visiteur.ConstructeurDeComposantsRecursif;
import fr.bruju.rmeventreader.implementationexec.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementationexec.formulatracker.operateurdesimplification.Maillon;

// TODO : devrait être intégré au template de base de constructeur de composants ?

/**
 * Maillon retirant les ternaires renvoyant la même valeur dans tous les cas.
 * 
 * @author Bruju
 *
 */
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
