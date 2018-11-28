package fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression;

import fr.bruju.rmdechiffreur.modele.OpMathematique;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.visiteurs.VisiteurDExpression;

import java.util.Objects;

public class Calcul implements Expression {
	public final Expression gauche;
	public final OpMathematique operande;
	public final Expression droite;
	
	public Calcul(Expression gauche, OpMathematique operande, Expression droite) {
		this.gauche = gauche;
		this.operande = operande;
		this.droite = droite;
	}




	@Override
	public String getString() {
		return "(" + gauche.getString() + " " + operande.symbole + " " + droite.getString() + ")";
	}

	@Override
	public String getStringAvecPriorite(int prioriteActuelle) {
		int prioriteOperateur;

		switch (operande) {
			case PLUS:
			case MOINS:
				prioriteOperateur = 1;
				break;
			case FOIS:
			case MODULO:
			case DIVISE:
				prioriteOperateur = 2;
				break;
			case AFFECTATION: // Impossible
			default:
				prioriteOperateur = 0;
				break;
		}

		// Si un x a une sous expression un +, on veut que + mette des parenthèses

		if (prioriteOperateur == 1) { // est un +
			if (prioriteActuelle == 2) { // Appelé par un x
				return "(" + gauche.getStringAvecPriorite(prioriteOperateur) + " " +
						operande.symbole + " " + droite.getStringAvecPriorite(prioriteOperateur) + ")";
			}
		}


		return gauche.getStringAvecPriorite(prioriteOperateur) + " " +
				operande.symbole + " " + droite.getStringAvecPriorite(prioriteOperateur);
	}

	@Override
	public void accept(VisiteurDExpression visiteurDExpression) {
		visiteurDExpression.visit(this);
	}
	


	@Override
	public Integer evaluer() {
		Integer evalGauche = gauche.evaluer();
		Integer evalDroite = droite.evaluer();
		
		if (evalGauche == null || evalDroite == null) {
			return null;
		} else {
			return operande.calculer(evalGauche, evalDroite);
		}
	}




	@Override
	public int hashCode() {
		return Objects.hash(gauche, operande, droite);
	}




	@Override
	public boolean equals(Object object) {
		if (object instanceof Calcul) {
			Calcul that = (Calcul) object;
			return Objects.equals(this.gauche, that.gauche) && Objects.equals(this.operande, that.operande)
					&& Objects.equals(this.droite, that.droite);
		}
		return false;
	}
	
	
}
