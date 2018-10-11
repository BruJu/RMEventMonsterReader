package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.inliner;

import java.util.HashSet;
import java.util.Set;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.AgregatDeVariables;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Calcul;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Constante;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.NombreAleatoire;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.VariableInstanciee;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.VisiteurDExpression;

public class ListeurDePresence implements VisiteurDExpression {	
	public final Set<VariableInstanciee> variablesPresentes = new HashSet<>();

	@Override
	public void visit(AgregatDeVariables composant) {
		composant.ajouterVariables(variablesPresentes);
	}

	@Override
	public void visit(Calcul composant) {
		visit(composant.gauche);
		visit(composant.droite);
	}
	
	@Override
	public void visit(VariableInstanciee composant) {
		variablesPresentes.add(composant);
	}

	@Override
	public void visit(Constante composant) {
	}

	@Override
	public void visit(NombreAleatoire composant) {
	}
}
