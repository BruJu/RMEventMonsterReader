package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.inliner;


import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.*;

public abstract class Evaluateur implements VisiteurDExpression {
	private boolean possedeUneVariable = false;
	protected int resultat;

	public Integer evaluer(Expression expression) {
		resultat = 0;
		possedeUneVariable = false;
		visit(expression);
		return possedeUneVariable ? null : resultat;
	}

	@Override
	public void visit(Calcul composant) {
		visit(composant.gauche);
		int gauche = resultat;
		visit(composant.droite);
		int droite = resultat;
		resultat = composant.operande.calculer(gauche, droite);
	}

	@Override
	public void visit(Constante composant) {
		resultat = composant.valeur;
	}

	@Override
	public void visit(ExprVariable composant) {
		possedeUneVariable = true;
	}

	@Override
	public void visit(Borne composant) {
		visit(composant.variable);
		int gauche = resultat;
		visit(composant.borne);
		int borne = resultat;

		if (composant.estBorneMax) {
			resultat = Math.max(gauche, borne);
		} else {
			resultat = Math.min(gauche, borne);
		}
	}


	public static class Minimum extends Evaluateur {
		@Override
		public void visit(NombreAleatoire composant) {
			resultat = composant.valeurMin;
		}
	}

	public static class Maximum extends Evaluateur {
		@Override
		public void visit(NombreAleatoire composant) {
			resultat = composant.valeurMax;
		}
	}
}
