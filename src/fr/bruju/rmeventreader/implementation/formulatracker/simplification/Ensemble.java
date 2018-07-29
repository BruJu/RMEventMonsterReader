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
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurRetourneur;


/*
 * EVALUATEUR DE BORNES + CLASSE ENSEMBLE
 * 
 * Ces classes sont à tester / retravailler / intégrer dans le cadre d'une évaluation plus fine
 * 
 * A supprimer ?
 * 
 */


public class Ensemble {
	public static enum Type {
		Booleen, Nombre
	};

	public final Type type;

	// Booleen
	public final boolean valeur;

	// Nombre
	public final int min;
	public final int max;
	public final int nbDeValeurs;

	public Ensemble(boolean b) {
		type = Type.Booleen;
		valeur = b;

		min = max = nbDeValeurs = -1;
	}

	public Ensemble(int valeur) {
		type = Type.Nombre;

		this.min = valeur;
		this.max = valeur;
		nbDeValeurs = 1;

		this.valeur = false;
	}

	public Ensemble(int min, int max) {
		type = Type.Nombre;
		this.min = min;
		this.max = max;
		nbDeValeurs = max - min + 1;

		this.valeur = false;
	}

	public boolean estVide() {
		return nbDeValeurs == 0;
	}

	public boolean aUneValeur() {
		if (nbDeValeurs == 0)
			return false;

		return min == max;
	}

	public Ensemble appliquerOperation(Operator operation, Ensemble autre) {
		switch (operation) {
		case PLUS:
			return ajouter(autre);
		case DIFFERENT:
			return different(autre);
		case DIVIDE:
		case TIMES:
			return multiplier(autre, operation);
		case IDENTIQUE:
			return identique(autre);
		case INF:
			return inf(autre);
		case INFEGAL:
			return infegal(autre);
		case MINUS:
			return moins(autre);
		case MODULO:
			return modulo(autre);
		case SUP:
			return sup(autre);
		case SUPEGAL:
			return supegal(autre);
		default:
			return null;
		}
	}

	/*
	 * RENVOI D'ENSEMBLES BOOLEENS
	 */

	private Ensemble different(Ensemble autre) {
		if (aUneValeur() && autre.aUneValeur()) {
			if (min == autre.min) {
				return new Ensemble(true);
			} else {
				return new Ensemble(false);
			}
		}

		return null;
	}

	private Ensemble identique(Ensemble autre) {
		if (this.min > autre.min) {
			return autre.identique(this);
		}

		if (aUneValeur() && autre.aUneValeur()) {
			return new Ensemble(this.min == autre.min);
		}

		if (this.max < autre.min) {
			// Totalement disjoincts
			return new Ensemble(false);
		} else { // this.max >= autre.min
			return null;
		}
	}

	private Ensemble inf(Ensemble autre) {
		Ensemble inverse = autre.supegal(this);

		return inverse;
	}

	private Ensemble sup(Ensemble autre) {
		if (min >= autre.max)
			return new Ensemble(false);

		if (max < autre.min) {
			return new Ensemble(true);
		}

		return null;
	}

	private Ensemble infegal(Ensemble autre) {
		return inf(new Ensemble(autre.min + 1, autre.max + 1));
	}

	private Ensemble supegal(Ensemble autre) {
		return sup(new Ensemble(autre.min - 1, autre.max - 1));
	}

	/*
	 * RENVOI D'ENSEMBLES NUMERIQUES
	 */

	private Ensemble multiplier(Ensemble autre, Operator operator) {
		int v00 = operator.compute(min, autre.min);
		int v01 = operator.compute(min, autre.max);
		int v10 = operator.compute(max, autre.min);
		int v11 = operator.compute(max, autre.max);

		int min = Math.min(Math.min(v00, v01), Math.min(v10, v11));
		int max = Math.max(Math.max(v00, v01), Math.max(v10, v11));

		return new Ensemble(min, max);
	}

	private Ensemble ajouter(Ensemble autre) {
		return new Ensemble(min + autre.min, max + autre.max);
	}

	private Ensemble moins(Ensemble autre) {
		return new Ensemble(min - autre.max, max - autre.min);
	}

	private Ensemble modulo(Ensemble autre) {
		if (aUneValeur() && autre.aUneValeur()) {
			return new Ensemble(this.min % autre.min);
		}

		return null;
	}

	
	
	
	
	public static class EvaluateurBorne extends VisiteurRetourneur<Ensemble> {

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

}
