package fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression;

import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.visiteurs.VisiteurDExpression;

public interface Expression {

	public String getString();

	public void accept(VisiteurDExpression visiteurDExpression);
	
	public Integer evaluer();

}
