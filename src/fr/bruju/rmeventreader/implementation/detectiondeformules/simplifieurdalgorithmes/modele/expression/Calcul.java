package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression;

import fr.bruju.rmdechiffreur.modele.OpMathematique;

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

}
