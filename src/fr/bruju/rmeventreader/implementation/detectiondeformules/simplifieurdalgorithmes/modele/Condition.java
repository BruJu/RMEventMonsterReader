package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele;

import fr.bruju.rmdechiffreur.modele.Comparateur;

public class Condition {
	public final Expression gauche;
	public final Comparateur comparateur;
	public final Expression droite;
	
	public Condition(Expression gauche, Comparateur comparateur, Expression droite) {
		this.gauche = gauche;
		this.comparateur = comparateur;
		this.droite = droite;
	}
}
