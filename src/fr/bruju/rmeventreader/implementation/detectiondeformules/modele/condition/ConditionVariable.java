package fr.bruju.rmeventreader.implementation.detectiondeformules.modele.condition;

import fr.bruju.rmdechiffreur.modele.Comparateur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.Constante;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.Expression;

import java.util.Objects;

/**
 * Une condition portant sur la comparaison de deux expressions.
 */
public class ConditionVariable implements Condition {
	public final Expression gauche;
	public final Comparateur comparateur;
	public final Expression droite;

	/**
	 * Crée une condition portant sur une variable aisni que une variable ou une constante
	 * @param gauche La variable
	 * @param comparateur L'opérateur de comparaison
	 * @param droite La variable ou la constante
	 */
	public ConditionVariable(Expression gauche, Comparateur comparateur, Expression droite) {
		this.gauche = gauche;
		this.comparateur = comparateur;
		this.droite = droite;
	}

	/**
	 * Construit une condition à partir d'une condition portant sur un interrupteur.
	 * <br>Converti en gauche = 1 (vrai) ou gauche != 1 (faux)
	 * @param gauche L'expression correspondant à la valeur de l'interrupteur
	 * @param estVrai L'état souhaité de l'interrupteur
	 */
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
	public Boolean evaluer() {
		Integer valeurGauche = gauche.evaluer();
		Integer valeurDroite = droite.evaluer();
		
		if (valeurGauche == null || valeurDroite == null) {
			return null;
		} else {
			return comparateur.test(valeurGauche, valeurDroite);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ConditionVariable that = (ConditionVariable) o;
		return Objects.equals(gauche, that.gauche) &&
				comparateur == that.comparateur &&
				Objects.equals(droite, that.droite);
	}

	@Override
	public int hashCode() {
		return Objects.hash(gauche, comparateur, droite);
	}
}
