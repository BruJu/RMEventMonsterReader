package fr.bruju.rmeventreader.implementation.detectiondeformules.modele.visiteurs;

import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.*;

public abstract class VisiteurReecrivainDExpression implements VisiteurDExpression {
	private Expression expression;
	
	public Expression explorer(Calcul composant) {
		Expression gauche = explorer(composant.gauche);
		Expression droite = explorer(composant.droite);
		
		if (gauche == composant.gauche && droite == composant.droite) {
			return composant;
		} else if (gauche == null || droite == null) {
			return null;
		} else {
			return new Calcul(gauche, composant.operande, droite);
		}
	}

	public Expression explorer(Borne composant) {
		Expression gauche = explorer(composant.variable);
		Expression droite = explorer(composant.borne);
		
		if (gauche == composant.variable && droite == composant.borne) {
			return composant;
		} else if (gauche == null || droite == null) {
			return null;
		} else {
			return new Borne(gauche, droite, composant.estBorneMin);
		}
	}
	
	public Expression explorer(Constante composant) {
		return composant;
	}
	public Expression explorer(NombreAleatoire composant) {
		return composant;
	}
	public Expression explorer(ExprVariable composant) {
		return composant;
	}

	public Expression explorer(Statistique composant) {
		return explorer((ExprVariable) composant);
	}
	
	public final Expression explorer(Expression composant) {
		visit(composant);
		return expression;
	}

	@Override
	public final void visit(Calcul composant) {
		expression = explorer(composant);
	}

	@Override
	public final void visit(Constante composant) {
		expression = explorer(composant);
	}

	@Override
	public final void visit(NombreAleatoire composant) {
		expression = explorer(composant);
	}

	@Override
	public final void visit(ExprVariable composant) {
		expression = explorer(composant);
	}

	@Override
	public final void visit(Borne composant) {
		expression = explorer(composant);
	}

	@Override
	public final void visit(Statistique composant) {
		expression = explorer(composant);
	}
}
