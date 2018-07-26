package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.ConstructeurDeComposantR;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;

public class Resolveur extends ConstructeurDeComposantR implements Maillon {

	@Override
	public void traiter(Attaques attaques) {
		attaques.transformerComposants(this::traiter);
	}
	
	@Override
	protected Composant traiter(CVariable cVariable) {
		Valeur gauche = (Valeur) traiter(cVariable.gauche);
		Valeur droite = (Valeur) traiter(cVariable.droite);

			CVariable condition = new CVariable(gauche, cVariable.operateur, droite);
			Boolean r = tenterDEvaluer(condition);

			if (r != null) {
				return CFixe.get(r);
			} else {
				return condition;
			}
		}

}
