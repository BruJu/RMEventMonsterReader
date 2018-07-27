package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.ConstructeurDeComposantR;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;

public class MaillonRetirerSiInutile extends ConstructeurDeComposantR implements Maillon {

	@Override
	public void traiter(Attaques attaques) {
		attaques.transformerComposants(this::traiter);
	}

	@Override
	protected Composant traiter(BTernaire boutonTernaire) {
		Condition bc = (Condition) traiter(boutonTernaire.condition);
		Bouton bf = (Bouton) traiter(boutonTernaire.siFaux);
		Bouton bv = (Bouton) traiter(boutonTernaire.siVrai);
		
		if (bv.equals(bf)) {
			return bf;
		} else {
			if (bc == boutonTernaire.condition && bv == boutonTernaire.siVrai && bf == boutonTernaire.siFaux) {
				return boutonTernaire;
			} else {
				return new BTernaire(bc, bv, bf);
			}
		}
	}

	@Override
	protected Composant traiter(VTernaire variableTernaire) {
		Condition bc = (Condition) traiter(variableTernaire.condition);
		Valeur bf = (Valeur) traiter(variableTernaire.siFaux);
		Valeur bv = (Valeur) traiter(variableTernaire.siVrai);
		
		if (bv.equals(bf)) {
			return bf;
		} else {
			if (bc == variableTernaire.condition && bv == variableTernaire.siVrai && bf == variableTernaire.siFaux) {
				return variableTernaire;
			} else {
				return new VTernaire(bc, bv, bf);
			}
		}
	}

	
	
	
}
