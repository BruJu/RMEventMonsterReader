package fr.bruju.rmeventreader.implementationexec.formulatracker.operateurdesimplification.simples;

import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.visiteur.ConstructeurDeComposantsRecursif;
import fr.bruju.rmeventreader.implementationexec.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementationexec.formulatracker.operateurdesimplification.Maillon;

/**
 * Transforme les formules pour rendre fausses toutes les conditions sur les possessions d'armes.
 * 
 * @author Bruju
 *
 */
public class MNePossedeAucunObjet extends ConstructeurDeComposantsRecursif implements Maillon  {
	@Override
	public void traiter(Attaques attaques) {
		attaques.transformerComposants(this::traiter);
	}

	@Override
	protected Composant modifier(CArme conditionArme) {
		return CFixe.get(false);
	}
}
