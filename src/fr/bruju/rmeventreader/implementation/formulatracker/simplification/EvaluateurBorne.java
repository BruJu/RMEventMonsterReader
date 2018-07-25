package fr.bruju.rmeventreader.implementation.formulatracker.simplification;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
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
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurRetourneur;

public class EvaluateurBorne extends VisiteurRetourneur<int[]> {

	// Booléens

	@Override
	protected int[] traiter(BConstant boutonConstant) {
		return boutonConstant.value ? new int[] { 1 } : new int[] { 0 };
	}

	@Override
	protected int[] traiter(BTernaire boutonTernaire) {
		int[] condi = traiter(boutonTernaire.condition);

		if (condi == null) {
			return null;
		} else {
			return condi[0] == 1 ? traiter(boutonTernaire.siVrai) : traiter(boutonTernaire.siFaux);
		}
	}

	@Override
	protected int[] traiter(CSwitch conditionSwitch) {
		int[] v = traiter(conditionSwitch.interrupteur);

		if (v == null)
			return null;

		boolean vb = v[0] == 1;

		return vb == conditionSwitch.valeur ? new int[] { 1 } : new int[] { 0 };
	}

	@Override
	protected int[] traiter(CVariable conditionVariable) {
		int[] gauche = traiter(conditionVariable.gauche);
		if (gauche == null)
			return null;

		int[] droite = traiter(conditionVariable.droite);
		if (droite == null)
			return null;

		int valeurMin = Math.min(gauche[0], droite[0]);
		int valeurMax = Math.max(gauche[1], droite[1]);

		switch (conditionVariable.operateur) {
		case IDENTIQUE:
			if (gauche[0] == droite[0] && gauche[1] == droite[1] && gauche[0] == gauche[1]) {
				return new int[] { 1 };
			} else {
				return new int[] { 0 };
			}
		case DIFFERENT:
			return null;
		case INF:
			break;
		case INFEGAL:
			break;
		case SUP:
			break;
		case SUPEGAL:
			break;
		default:
			return null;
		}

		// TODO
		return null;
	}

	@Override
	protected int[] traiter(VAleatoire variableAleatoire) {
		return new int[] { variableAleatoire.min, variableAleatoire.max,
				variableAleatoire.max - variableAleatoire.min + 1 };
	}

	@Override
	protected int[] traiter(VCalcul variableCalcul) {
		int[] gauche = traiter(variableCalcul.gauche);
		int[] droite = traiter(variableCalcul.droite);

		if (gauche == null || droite == null)
			return null;

		int v00, v01, v10, v11;

		switch (variableCalcul.operateur) {
		case PLUS:
			return new int[] { gauche[0] + droite[0], gauche[0] + droite[0] };
		case MINUS:
			return new int[] { gauche[0] - droite[1], gauche[1] - droite[0] };
		case TIMES:
			v00 = gauche[0] * droite[0];
			v01 = gauche[0] * droite[1];
			v10 = gauche[1] * droite[0];
			v11 = gauche[1] * droite[1];

			return new int[] { Math.min(Math.min(v00, v01), Math.min(v10, v11)),
					Math.max(Math.max(v00, v01), Math.max(v10, v11)) };
		case DIVIDE:
			if (droite[0] == 0 || droite[1] == 0)
				return null;
			
			v00 = gauche[0] / droite[0];
			v01 = gauche[0] / droite[1];
			v10 = gauche[1] / droite[0];
			v11 = gauche[1] / droite[1];

			return new int[] { Math.min(Math.min(v00, v01), Math.min(v10, v11)),
					Math.max(Math.max(v00, v01), Math.max(v10, v11)) };
		case MODULO:
			if (gauche[0] != gauche[1] || droite[0] != droite[1]) {
				return null;
			}

			return new int[] { gauche[0] % droite[0], gauche[1] % droite[1] };
		default:
			return null;
		}
	}

	@Override
	protected int[] traiter(VConstante variableConstante) {
		return new int[] {variableConstante.valeur, variableConstante.valeur};
	}

	@Override
	protected int[] traiter(VTernaire variableTernaire) {
		int[] c = traiter(variableTernaire.condition);
		
		if (c[0] == 1) {
			return traiter(variableTernaire.siVrai);
		} else {
			return traiter(variableTernaire.siFaux);
		}
	}

	// Non valuable

	@Override
	protected int[] traiter(CArme composant) {
		return comportementParDefaut(composant);
	}

	@Override
	protected int[] traiter(VStatistique composant) {
		return comportementParDefaut(composant);
	}

	@Override
	protected int[] traiter(VBase composant) {
		return comportementParDefaut(composant);
	}

	@Override
	protected int[] traiter(BBase composant) {
		return comportementParDefaut(composant);
	}

	@Override
	protected int[] comportementParDefaut(Composant composant) {
		return null;
	}

}
