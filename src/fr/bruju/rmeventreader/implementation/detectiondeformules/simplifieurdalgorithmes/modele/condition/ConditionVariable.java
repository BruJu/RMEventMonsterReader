package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition;

import fr.bruju.rmdechiffreur.modele.Comparateur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Constante;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Expression;

public class ConditionVariable implements Condition {
	public final Expression gauche;
	public final Comparateur comparateur;
	public final Expression droite;
	
	public ConditionVariable(Expression gauche, Comparateur comparateur, Expression droite) {
		this.gauche = gauche;
		this.comparateur = comparateur;
		this.droite = droite;
	}

	
	public ConditionVariable(Expression gauche, boolean estVrai) {
		this.gauche = gauche;
		this.comparateur = estVrai ? Comparateur.IDENTIQUE : Comparateur.DIFFERENT;
		this.droite = new Constante(1);
	}


	@Override
	public String getString() {
		return gauche.getString() + " " + comparateur + " " + droite.getString();
	}


	@Override
	public Boolean tester() {
		Integer valeurGauche = gauche.evaluer();
		Integer valeurDroite = droite.evaluer();
		
		if (valeurGauche == null || valeurDroite == null) {
			return null;
		} else {
			return comparateur.test(valeurGauche, valeurDroite);
		}
	}

	@Override
	public Condition inverser() {
		return new ConditionVariable(gauche, comparateur.revert(), droite);
	}


}
