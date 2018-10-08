package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition;

import fr.bruju.rmdechiffreur.modele.Comparateur;
import fr.bruju.rmdechiffreur.modele.Condition.CondInterrupteur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Constante;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.ExprVariable;
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

	
	public ConditionVariable(CondInterrupteur condInterrupteur) {
		this.gauche = new ExprVariable(-condInterrupteur.interrupteur);
		this.comparateur = condInterrupteur.etat ? Comparateur.IDENTIQUE : Comparateur.DIFFERENT;
		this.droite = new Constante(1);
	}


	@Override
	public String getString() {
		return gauche.getString() + " " + comparateur + " " + droite.getString();
	}
}
