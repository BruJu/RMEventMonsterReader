package fr.bruju.rmeventreader.implementation.formulatracker.simplification;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;

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
		return inf(new Ensemble(autre.min + 1, autre.max +1));
	}

	private Ensemble supegal(Ensemble autre) {
		return sup(new Ensemble(autre.min - 1, autre.max -1));
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


}
