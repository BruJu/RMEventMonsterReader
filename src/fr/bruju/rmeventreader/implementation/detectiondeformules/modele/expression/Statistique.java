package fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression;

import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.personnage.Personnage;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.visiteurs.VisiteurDExpression;

public class Statistique extends ExprVariable {
	public final Personnage personnage;
	public final String nom;

	public Statistique(int idVariable, Personnage personnage, String nom) {
		super(idVariable);
		this.personnage = personnage;
		this.nom = nom;
	}

	@Override
	public String getString() {
		return personnage.getNom() + '.' + nom;
	}

	@Override
	public void accept(VisiteurDExpression visiteurDExpression) {
		visiteurDExpression.visit(this);
	}
}
