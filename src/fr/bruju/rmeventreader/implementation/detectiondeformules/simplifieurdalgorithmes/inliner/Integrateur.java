package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.inliner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.AgregatDeVariables;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Calcul;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Constante;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Expression;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.NombreAleatoire;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.VariableInstanciee;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.VisiteurDExpression;

public class Integrateur implements VisiteurDExpression {
	private Map<VariableInstanciee, Expression> variablesAIntegrer = new HashMap<>();
	private Expression resultat;

	public Integrateur(List<InstructionAffectation> instructionsAIntegrer) {
		for (InstructionAffectation instruction : instructionsAIntegrer) {
			variablesAIntegrer.put(instruction.variableAssignee, instruction.expression);
		}
	}

	@Override
	public void visit(VariableInstanciee composant) {
		resultat = variablesAIntegrer.getOrDefault(composant, composant);
	}

	@Override
	public void visit(Calcul composant) {
		Expression origine = resultat;
		Expression gauche;
		Expression droite;
		
		visit(composant.gauche);
		gauche = resultat;
		
		visit(composant.droite);
		droite = resultat;
		
		resultat = origine;

		resultat = new Calcul(gauche, composant.operande, droite);
	}
	
	@Override
	public void visit(AgregatDeVariables composant) {
		resultat = composant;
	}

	@Override
	public void visit(Constante composant) {
		resultat = composant;
	}

	@Override
	public void visit(NombreAleatoire composant) {
		resultat = composant;
	}

	public Expression getResultat() {
		return resultat;
	}

}
