package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.inliner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionGenerale;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.AgregatDeVariables;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Calcul;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Constante;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Expression;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.NombreAleatoire;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.VariableInstanciee;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.VisiteurDExpression;

public class Integrateur implements VisiteurDExpression {
	private Map<VariableInstanciee, InstructionAffectation> variablesAIntegrerInterne;
	private Map<InstructionGenerale, Map<VariableInstanciee, InstructionAffectation>> variablesAIntegrer;
	private Expression resultat;

	public Integrateur(InstructionAffectation instructionAffectation, 
			Map<InstructionGenerale, Map<VariableInstanciee, InstructionAffectation>> variablesAIntegrer) {
		this.variablesAIntegrerInterne = variablesAIntegrer.get(instructionAffectation);
		this.variablesAIntegrer = variablesAIntegrer;
		
		if (variablesAIntegrerInterne == null) {
			variablesAIntegrerInterne = new HashMap<>();
		}
	}

	@Override
	public void visit(VariableInstanciee composant) {
		InstructionAffectation reecriture = variablesAIntegrerInterne.get(composant);
		
		if (reecriture == null) {
			resultat = composant;
		} else {
			Expression expression = reecriture.expression;
			Integrateur fils = new Integrateur(reecriture, variablesAIntegrer);
			fils.visit(expression);
			resultat = fils.getResultat();
		}
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
