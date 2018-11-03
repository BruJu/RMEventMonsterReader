package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.transformations.inliner;

import java.util.HashSet;
import java.util.Set;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Borne;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Calcul;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Constante;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.NombreAleatoire;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.ExprVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.VisiteurDExpression;

public class ListeurDePresence implements VisiteurDExpression {	
	public final Set<ExprVariable> variablesPresentes = new HashSet<>();


	@Override
	public void visit(Calcul composant) {
		visit(composant.gauche);
		visit(composant.droite);
	}
	
	@Override
	public void visit(ExprVariable composant) {
		variablesPresentes.add(composant);
	}

	@Override
	public void visit(Constante composant) {
	}

	@Override
	public void visit(NombreAleatoire composant) {
	}

	@Override
	public void visit(Borne composant) {
		visit(composant.variable);
		visit(composant.borne);
	}
}
