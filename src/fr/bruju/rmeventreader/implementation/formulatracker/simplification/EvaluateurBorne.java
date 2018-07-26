package fr.bruju.rmeventreader.implementation.formulatracker.simplification;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BConstant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VAleatoire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VStatistique;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurRetourneur;

public class EvaluateurBorne extends VisiteurRetourneur<Ensemble> {

	public Integer evaluer(Valeur v) {
		Ensemble e = traiter(v);
		
		if (e == null)
			return null;
		
		if (e.aUneValeur()) {
			return e.min;
		}
		
		return null;
	}
	

	// Booléens

	@Override
	protected Ensemble traiter(BConstant boutonConstant) {
		return new Ensemble(boutonConstant.value);
	}

	@Override
	protected Ensemble traiter(BTernaire boutonTernaire) {
		Ensemble v = traiter(boutonTernaire.condition);
		
		if (v == null)
			return null;
		
		return (v.valeur) ? traiter(boutonTernaire.siVrai) : traiter(boutonTernaire.siFaux);
	}

	@Override
	protected Ensemble traiter(CSwitch conditionSwitch) {
		Ensemble v = traiter(conditionSwitch.interrupteur);
		
		if (conditionSwitch.valeur) {
			return v;
		} else {
			if (v == null)
				return null;
			
			return new Ensemble(!v.valeur);
		}
	}

	@Override
	protected Ensemble traiter(CVariable conditionVariable) {
		Ensemble gauche = traiter(conditionVariable.gauche);
		Ensemble droite = traiter(conditionVariable.droite);
		
		if (gauche == null || droite == null)
			return null;
		
		return gauche.appliquerOperation(conditionVariable.operateur, droite);
	}

	@Override
	protected Ensemble traiter(VAleatoire variableAleatoire) {
		return new Ensemble(variableAleatoire.min, variableAleatoire.max);
	}

	@Override
	protected Ensemble traiter(VCalcul variableCalcul) {
		Ensemble gauche = traiter(variableCalcul.gauche);
		Ensemble droite = traiter(variableCalcul.droite);
		
		if (gauche == null || droite == null)
			return null;
		
		return gauche.appliquerOperation(variableCalcul.operateur, droite);
	}

	@Override
	protected Ensemble traiter(VConstante variableConstante) {
		return new Ensemble(variableConstante.valeur);
	}

	@Override
	protected Ensemble traiter(VTernaire variableTernaire) {
		Ensemble c = traiter(variableTernaire.condition);
		
		if (c == null) {
			return null;
		}
		
		if (c.valeur) {
			return traiter(variableTernaire.siVrai);
		} else {
			return traiter(variableTernaire.siFaux);
		}
	}

	// Non valuable

	@Override
	protected Ensemble traiter(CArme composant) {
		return comportementParDefaut(composant);
	}

	@Override
	protected Ensemble traiter(VStatistique composant) {
		return comportementParDefaut(composant);
	}

	@Override
	protected Ensemble traiter(VBase composant) {
		return comportementParDefaut(composant);
	}

	@Override
	protected Ensemble traiter(BBase composant) {
		return comportementParDefaut(composant);
	}

	@Override
	protected Ensemble comportementParDefaut(Composant composant) {
		return null;
	}

}
