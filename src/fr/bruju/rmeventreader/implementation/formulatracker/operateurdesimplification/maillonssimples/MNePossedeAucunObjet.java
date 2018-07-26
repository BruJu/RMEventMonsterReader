package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.maillonssimples;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.ConstructeurDeComposantR;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;

/**
 * Transforme les formules pour rendre fausses toutes les conditions sur les possessions d'armes.
 * @author Bruju
 *
 */
public class MNePossedeAucunObjet extends ConstructeurDeComposantR implements Maillon  {
	@Override
	public void traiter(Attaques attaques) {
		attaques.transformerComposants(this::traiter);
	}

	@Override
	protected Composant traiter(CArme conditionArme) {
		return CFixe.get(false);
	}
}
