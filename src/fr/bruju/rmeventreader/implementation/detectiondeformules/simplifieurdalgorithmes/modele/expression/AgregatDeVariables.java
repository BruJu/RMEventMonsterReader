package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression;

import static fr.bruju.rmeventreader.ProjetS.PROJET;

import java.util.HashSet;
import java.util.Set;

public class AgregatDeVariables implements VariableUtilisee {
	public final CaseMemoire caseMemoire;

	
	private final Set<VariableInstanciee> ensemble;


	public AgregatDeVariables(Set<VariableInstanciee> ensemble) {
		this.ensemble = ensemble;
		caseMemoire = ensemble.iterator().next().caseMemoire;
	}


	public static VariableUtilisee combiner(VariableUtilisee variableUtilisee, VariableUtilisee variableUtilisee2) {
		Set<VariableInstanciee> ensemble = new HashSet<>();
		
		variableUtilisee.ajouterVariables(ensemble);
		variableUtilisee2.ajouterVariables(ensemble);
		
		if (ensemble.size() == 1) {
			return ensemble.iterator().next();
		} else {
			return new AgregatDeVariables(ensemble);
		}
	}
	
	
	@Override
	public String getString() {
		StringBuilder sb = new StringBuilder();
		String nom;
		int coef;
		
		if (caseMemoire.numeroCase < 0) {
			coef = -1;
			sb.append("S[");
			nom = PROJET.extraireInterrupteur(-caseMemoire.numeroCase);
		} else {
			coef = 1;
			sb.append("V[");
			nom = PROJET.extraireVariable(caseMemoire.numeroCase);
		}
		
		sb.append(String.format("%04d", caseMemoire.numeroCase * coef));
		
		for (VariableInstanciee variable : ensemble) {
			sb.append(".")
		  	  .append(String.format("%03d", variable.id));
		}
		sb.append(":")
		  .append(nom.trim())
		  .append("]");
		
		return sb.toString();
	}

	@Override
	public VariableInstanciee nouvelleInstance() {
		return ensemble.iterator().next().nouvelleInstance();
	}


	@Override
	public void ajouterVariables(Set<VariableInstanciee> ensemble) {
		ensemble.addAll(this.ensemble);
	}


	@Override
	public void accept(VisiteurDExpression visiteurDExpression) {
		visiteurDExpression.visit(this);
	}


}
